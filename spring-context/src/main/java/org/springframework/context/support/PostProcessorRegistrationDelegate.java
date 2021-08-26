/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.lang.Nullable;

/**
 * Delegate for AbstractApplicationContext's post-processor handling.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 4.0
 */
final class PostProcessorRegistrationDelegate {

	private PostProcessorRegistrationDelegate() {
	}


	/**
	 *
	 * @param beanFactory
	 * @param beanFactoryPostProcessors 执行的时候传进来的bfpp有三种类型 BeanDefinitionRegistryPostProcessors BeanFactoryPostProcessor 和外部传进来的类似MyBeanFactoryPostProcessor
	 *            beanFactoryPostProcessors 修改的是beanFactory属性
	 *            BeanDefinitionRegistryPostProcessors 修改的是beanDefinition中的属性
	 */
	public static void invokeBeanFactoryPostProcessors(
			ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {

		// Invoke BeanDefinitionRegistryPostProcessors first, if any.
		// 首先 调用BeanDefinitionRegistryPostProcessors
		// 将已经执行过BFPP存储在set集合中，防止重复执行
		Set<String> processedBeans = new HashSet<>();

		// 判断beanFactory是否是BeanDefinitionRegistry类型，此处是DefaultListableBeanFactory,实现了BeanDefinitionRegistry接口，所以为true
		if (beanFactory instanceof BeanDefinitionRegistry) {
			//类型转换
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
			//此处注意BeanDefinitionRegistryPostProcessor 是BeanFactoryPostProcessor 的子接口
			//存放BeanFactoryPostProcessor集合
			List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
			//存放BeanDefinitionRegistryPostProcessors集合
			List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();

			//首先处理入参 BeanFactoryPostProcessor
			for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
				//如果是BeanDefinitionRegistryPostProcessor 强转并执行register接口方法 然后加入普通的父接口的集合 执行父接口的方法
				if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
					BeanDefinitionRegistryPostProcessor registryProcessor =
							(BeanDefinitionRegistryPostProcessor) postProcessor;
					//直接执行BeanDefinitionRegistryPostProcessor接口中定义的方法postProcessBeanDefinitionRegistry
					registryProcessor.postProcessBeanDefinitionRegistry(registry);
					//存放到register集合中 后续执行postProcessBeanFactory
					registryProcessors.add(registryProcessor);
				}
				else {
					//如果只是普通的BeanFactoryPostProcessor 存放到普通的集合中
					regularPostProcessors.add(postProcessor);
				}
			}

			// Do not initialize FactoryBeans here: We need to leave all regular beans
			// uninitialized to let the bean factory post-processors apply to them!
			// Separate between BeanDefinitionRegistryPostProcessors that implement
			// PriorityOrdered, Ordered, and the rest.
			//用于保存本次要执行的BeanDefinitionRegistryPostProcessor 跟外部传进来的无关 外部传参上面已经=执行了
			List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();
			/*--------------------------------------------------------------------------*/
			// First, invoke the BeanDefinitionRegistryPostProcessors that implement PriorityOrdered.
			// 首先执行 实现了PriorityOrdered接口的BeanDefinitionRegistryPostProcessors

			// 找到所有实现了BeanDefinitionRegistryPostProcessor接口的bean的beanName
			// 例如 ConfigurationClassPostProcessor 既实现了 bdrpp  也实现了 PriorityOrdered
			String[] postProcessorNames =
					beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			//检测是否实现了PriorityOrdered接口 满足就添加到本次需要执行的集合
			for (String ppName : postProcessorNames) {
				if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
					//获取对应的bean实例加入到本次需要执行的本次需要执行的集合
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					//ppName加入到set避免重复
					processedBeans.add(ppName);
				}
			}
			//排序集合
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			//添加到register子接口集合
			registryProcessors.addAll(currentRegistryProcessors);
			//遍历currentRegistryProcessors执行postProcessBeanDefinitionRegister()
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			//执行后清空本次执行集合
			currentRegistryProcessors.clear();
			/*--------------------------------------------------------------------------------*/
			// Next, invoke the BeanDefinitionRegistryPostProcessors that implement Ordered.
			// 然后 执行实现了ordered接口的BeanDefinitionRegistryPostProcessors
			// 再次调用beanFactory.getBeanNamesForType 原因在于上述执行中可能产生新的 BeanDefinitionRegistryPostProcessor
			postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			for (String ppName : postProcessorNames) {
				//检查是否在set去重集合中 是否实现了Ordered
				if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
					//获取对应的bean实例加入到本次需要执行的本次需要执行的集合 同上
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					//ppName加入到set避免重复 同上
					processedBeans.add(ppName);
				}
			}
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			registryProcessors.addAll(currentRegistryProcessors);
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			currentRegistryProcessors.clear();
			/*--------------------------------------------------------------------------*/

			// Finally, invoke all other BeanDefinitionRegistryPostProcessors until no further ones appear.
			// 最后执行所有的其他的BeanDefinitionRegistryPostProcessors 没有实现ordered接口的
			boolean reiterate = true;
			// 这里while循环是防止执行产生新的 BeanDefinitionRegistryPostProcessor
			while (reiterate) {
				reiterate = false;
				//获取所有 BeanDefinitionRegistryPostProcessor
				postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
				for (String ppName : postProcessorNames) {
					//如果有还没执行的
					if (!processedBeans.contains(ppName)) {
						currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
						processedBeans.add(ppName);
						reiterate = true;
					}
				}
				sortPostProcessors(currentRegistryProcessors, beanFactory);
				registryProcessors.addAll(currentRegistryProcessors);
				invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
				currentRegistryProcessors.clear();
			}

			/*--------------------------------------------------------------------------*/
			// Now, invoke the postProcessBeanFactory callback of all processors handled so far.
			// 调用 beanDefinitionRegisterPostProcessor 中的 postProcessBeanFactory
			invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
			// 调用 beanFactoryPostProcessor 中的 postProcessBeanFactory
			invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
		}

		else {
			// Invoke factory processors registered with the context instance.
			//如果beanFactory不属于 BeanDefinitionRegister 类型
			invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
		}


		/*--------------------------------------------------------------------------*/
		//到这里为止，入参 BeanFactoryPostProcessor 和 容器中的 BeanDefinitionRegisterPostProcessor
		//已经全部调用 接着处理所有容器中的 BeanFactoryPostProcessor
		//可能有些类只实现了 BeanFactoryPostProcessor 并没有实现 BeanDefinitionRegisterPostProcessor

		// Do not initialize FactoryBeans here: We need to leave all regular beans
		// uninitialized to let the bean factory post-processors apply to them!

		// 找到所有实现了 BeanFactoryPostProcessor 接口的bean的beanName 注意beanDefinition 是xml里面配置的
		// 前面是找实现了 BeanDefinitionRegistryPostProcessor 接口的
		String[] postProcessorNames =
				beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);

		// Separate between BeanFactoryPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.
		//存放实现了PriorityOrdered接口的 BeanFactoryPostProcessor 实例
		List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
		//用于存放实现了Ordered 接口的 BeanFactoryPostProcessor 的 beanName
		List<String> orderedPostProcessorNames = new ArrayList<>();
		//用于存放普通的 BeanFactoryPostProcessor 的 beanName
		List<String> nonOrderedPostProcessorNames = new ArrayList<>();
		for (String ppName : postProcessorNames) {
			//跳过已经执行过的
			if (processedBeans.contains(ppName)) {
				// skip - already processed in first phase above
			}
			else if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
			}
			else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			}
			else {
				nonOrderedPostProcessorNames.add(ppName);
			}
		}

		// First, invoke the BeanFactoryPostProcessors that implement PriorityOrdered.
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);

		// Next, invoke the BeanFactoryPostProcessors that implement Ordered.
		List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<>(orderedPostProcessorNames.size());
		for (String postProcessorName : orderedPostProcessorNames) {
			orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		sortPostProcessors(orderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);

		// Finally, invoke all other BeanFactoryPostProcessors.
		List<BeanFactoryPostProcessor> nonOrderedPostProcessors = new ArrayList<>(nonOrderedPostProcessorNames.size());
		for (String postProcessorName : nonOrderedPostProcessorNames) {
			nonOrderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);

		// Clear cached merged bean definitions since the post-processors might have
		// modified the original metadata, e.g. replacing placeholders in values...

		beanFactory.clearMetadataCache();
	}

	public static void registerBeanPostProcessors(
			ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {

		// 找到所有实现了BeanPostProcessor接口的类
		String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);

		// Register BeanPostProcessorChecker that logs an info message when
		// a bean is created during BeanPostProcessor instantiation, i.e. when
		// a bean is not eligible for getting processed by all BeanPostProcessors.

		// 记录下BeanPostProcessor的目标计数
		// 此处为什么要+1呢，原因非常简单，在此方法的最后会添加一个BeanPostProcessorChecker的类
		int beanProcessorTargetCount = beanFactory.getBeanPostProcessorCount() + 1 + postProcessorNames.length;
		// 添加BeanPostProcessorChecker(主要用于记录信息)到beanFactory中
		beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));

		// Separate between BeanPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.

		// 定义存放实现了PriorityOrdered接口的BeanPostProcessor集合
		List<BeanPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
		// 定义存放spring内部的BeanPostProcessor
		List<BeanPostProcessor> internalPostProcessors = new ArrayList<>();
		// 定义存放实现了Ordered接口的BeanPostProcessor的name集合
		List<String> orderedPostProcessorNames = new ArrayList<>();
		// 定义存放普通的BeanPostProcessor的name集合
		List<String> nonOrderedPostProcessorNames = new ArrayList<>();

		for (String ppName : postProcessorNames) {
			// 如果ppName对应的BeanPostProcessor实例实现了PriorityOrdered接口，则获取到ppName对应的BeanPostProcessor的实例添加到priorityOrderedPostProcessors中
			if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
				priorityOrderedPostProcessors.add(pp);
				// 如果ppName对应的BeanPostProcessor实例也实现了MergedBeanDefinitionPostProcessor接口，
				// 那么则将ppName对应的bean实例添加到internalPostProcessors中
				if (pp instanceof MergedBeanDefinitionPostProcessor) {
					internalPostProcessors.add(pp);
				}
			}
			// 如果ppName对应的BeanPostProcessor实例没有实现PriorityOrdered接口，但是实现了Ordered接口，那么将ppName对应的bean实例添加到orderedPostProcessorNames中
			else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			}
			else {
				// 否则将ppName添加到nonOrderedPostProcessorNames中
				nonOrderedPostProcessorNames.add(ppName);
			}
		}

		// First, register the BeanPostProcessors that implement PriorityOrdered.
		// 首先，对实现了PriorityOrdered接口的BeanPostProcessor实例进行排序操作
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		// 注册实现了PriorityOrdered接口的BeanPostProcessor实例添加到beanFactory中
		registerBeanPostProcessors(beanFactory, priorityOrderedPostProcessors);

		// Next, register the BeanPostProcessors that implement Ordered.
		List<BeanPostProcessor> orderedPostProcessors = new ArrayList<>(orderedPostProcessorNames.size());
		// 注册所有实现Ordered的beanPostProcessor
		for (String ppName : orderedPostProcessorNames) {
			// 根据ppName找到对应的BeanPostProcessor实例对象
			BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
			// 将实现了Ordered接口的BeanPostProcessor添加到orderedPostProcessors集合中
			orderedPostProcessors.add(pp);
			// 如果ppName对应的BeanPostProcessor实例也实现了MergedBeanDefinitionPostProcessor接口，
			// 那么则将ppName对应的bean实例添加到internalPostProcessors中
			if (pp instanceof MergedBeanDefinitionPostProcessor) {
				internalPostProcessors.add(pp);
			}
		}
		sortPostProcessors(orderedPostProcessors, beanFactory);
		//  注册实现了Ordered接口的BeanPostProcessor实例添加到beanFactory中
		registerBeanPostProcessors(beanFactory, orderedPostProcessors);

		// Now, register all regular BeanPostProcessors.
		// 创建存放没有实现PriorityOrdered和Ordered接口的BeanPostProcessor的集合
		List<BeanPostProcessor> nonOrderedPostProcessors = new ArrayList<>(nonOrderedPostProcessorNames.size());
		for (String ppName : nonOrderedPostProcessorNames) {
			BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
			nonOrderedPostProcessors.add(pp);
			// 如果ppName对应的BeanPostProcessor实例也实现了MergedBeanDefinitionPostProcessor接口，那么则将ppName对应的bean实例添加到internalPostProcessors中
			if (pp instanceof MergedBeanDefinitionPostProcessor) {
				internalPostProcessors.add(pp);
			}
		}
		//  注册没有实现PriorityOrdered和Ordered的BeanPostProcessor实例添加到beanFactory中
		registerBeanPostProcessors(beanFactory, nonOrderedPostProcessors);

		// Finally, re-register all internal BeanPostProcessors.
		sortPostProcessors(internalPostProcessors, beanFactory);
		// 注册所有实现了MergedBeanDefinitionPostProcessor类型的BeanPostProcessor到beanFactory中
		registerBeanPostProcessors(beanFactory, internalPostProcessors);

		// Re-register post-processor for detecting inner beans as ApplicationListeners,
		// moving it to the end of the processor chain (for picking up proxies etc).
		// 注册ApplicationListenerDetector到beanFactory中
		// prepareBeanFactory的时候已经注册过 这里是删除之前的 再把当前的添加的末尾
		beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));
	}

	private static void sortPostProcessors(List<?> postProcessors, ConfigurableListableBeanFactory beanFactory) {
		// Nothing to sort?
		if (postProcessors.size() <= 1) {
			return;
		}
		Comparator<Object> comparatorToUse = null;
		if (beanFactory instanceof DefaultListableBeanFactory) {
			comparatorToUse = ((DefaultListableBeanFactory) beanFactory).getDependencyComparator();
		}
		if (comparatorToUse == null) {
			comparatorToUse = OrderComparator.INSTANCE;
		}
		postProcessors.sort(comparatorToUse);
	}

	/**
	 * Invoke the given BeanDefinitionRegistryPostProcessor beans.
	 */
	private static void invokeBeanDefinitionRegistryPostProcessors(
			Collection<? extends BeanDefinitionRegistryPostProcessor> postProcessors, BeanDefinitionRegistry registry) {

		for (BeanDefinitionRegistryPostProcessor postProcessor : postProcessors) {
			postProcessor.postProcessBeanDefinitionRegistry(registry);
		}
	}

	/**
	 * Invoke the given BeanFactoryPostProcessor beans.
	 */
	private static void invokeBeanFactoryPostProcessors(
			Collection<? extends BeanFactoryPostProcessor> postProcessors, ConfigurableListableBeanFactory beanFactory) {

		for (BeanFactoryPostProcessor postProcessor : postProcessors) {
			postProcessor.postProcessBeanFactory(beanFactory);
		}
	}

	/**
	 * Register the given BeanPostProcessor beans.
	 */
	private static void registerBeanPostProcessors(
			ConfigurableListableBeanFactory beanFactory, List<BeanPostProcessor> postProcessors) {

		for (BeanPostProcessor postProcessor : postProcessors) {
			beanFactory.addBeanPostProcessor(postProcessor);
		}
	}


	/**
	 * BeanPostProcessor that logs an info message when a bean is created during
	 * BeanPostProcessor instantiation, i.e. when a bean is not eligible for
	 * getting processed by all BeanPostProcessors.
	 */
	private static final class BeanPostProcessorChecker implements BeanPostProcessor {

		private static final Log logger = LogFactory.getLog(BeanPostProcessorChecker.class);

		private final ConfigurableListableBeanFactory beanFactory;

		private final int beanPostProcessorTargetCount;

		public BeanPostProcessorChecker(ConfigurableListableBeanFactory beanFactory, int beanPostProcessorTargetCount) {
			this.beanFactory = beanFactory;
			this.beanPostProcessorTargetCount = beanPostProcessorTargetCount;
		}

		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName) {
			return bean;
		}

		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) {
			// 如果bean不是BeanPostProcessor实例 && beanName 不是 完全内部使用 && beanFactory当前注册的BeanPostProcessor
			// 数量小于 BeanPostProcessor目标数量
			if (!(bean instanceof BeanPostProcessor) && !isInfrastructureBean(beanName) &&
					this.beanFactory.getBeanPostProcessorCount() < this.beanPostProcessorTargetCount) {
				if (logger.isInfoEnabled()) {
					logger.info("Bean '" + beanName + "' of type [" + bean.getClass().getName() +
							"] is not eligible for getting processed by all BeanPostProcessors " +
							"(for example: not eligible for auto-proxying)");
				}
			}
			return bean;
		}

		/**
		 * 判断 beanName 是否是 完全内部使用
		 * @param beanName
		 * @return
		 */
		private boolean isInfrastructureBean(@Nullable String beanName) {
			//如果 beanName不为null && beanFactory包含具有beanName的beanDefinition对象
			if (beanName != null && this.beanFactory.containsBeanDefinition(beanName)) {
				//从beanFactory中获取beanName的BeanDefinition对象
				BeanDefinition bd = this.beanFactory.getBeanDefinition(beanName);
				//如果bd的角色是完全内部使用，返回true；否则返回false
				return (bd.getRole() == RootBeanDefinition.ROLE_INFRASTRUCTURE);
			}
			return false;
		}
	}

}
