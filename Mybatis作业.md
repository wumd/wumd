1、Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？

​	答：动态sql是指根据不同的参数，组装成不同结构的sql

​			动态sql包含使用判断条件来组装sql,这里使用了where标签和if标签，还包含使用循环来组装sql这里使用了where标签和foreach标签
			动态sql的执行原理是使用xml文本解析器解析xml文本，然后因为使用了固定的标签和格式，可以对其中的内容进行判断或者循环
​				

2、Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？

​	答：Mybatis支持延迟加载，它的原理是创建目标对象的代理对象，当调用目标方法时进入拦截器方法，在拦截器方法中再来根据懒加载的设置判断是否加载数据

3、Mybatis都有哪些Executor执行器？它们之间的区别是什么？

​	答：SimpleExecutor:每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象

​			ReuseExecutor：执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，用完后，不关闭Statement对象，而是放置于Map<String, Statement>内，供下一次使用。简言之，就是重复使用Statement对象。

​			BatchExecutor：执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理。与JDBC批处理相同。

4、简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？

​	答：一级、二级缓存的存储结构都是HashMap,但是一级缓存是SqlSession级别的，每个SqlSession实例对象的一级缓存互不影响，二级缓存是Mapper级别的，多个SqlSession实例对象操作同一个mapper时，它们之间是共享二级缓存的，一级、二级缓存都会在提交事务时刷新，一级缓存在SqlSession实例对象关闭时也会消失

5、简述Mybatis的插件运行原理，以及如何编写一个插件？

​	答：mybatis可以编写针对Executor、StatementHandler、ParameterHandler、ResultSetHandler四个接口的插件，mybatis使用JDK的动态代理为需要拦截的接口生成代理对象，然后实现接口的拦截方法，所以当执行需要拦截的接口方法时，会进入拦截方法（AOP面向切面编程的思想）

​			编写一个插件需要三步：

​				1.编写Intercepror接口的实现类

​				2.设置插件的签名，指定拦截的对象和方法

​				3.将插件注册到配置文件中