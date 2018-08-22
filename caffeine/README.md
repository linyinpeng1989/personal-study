[Caffeine](https://github.com/ben-manes/caffeine/wiki) ---  一个高性能的 Java 缓存库

缓存与ConcurrentMap非常相似，但并不相同。它们最大的区别在于：ConcurrentMap会持久保存添加到它的所有元素，直到它们被明确删除，
而缓存通常会自动回收对象，从而控制内存占用。


## 目录
- common：一些基础类
- population：加载策略。Caffeine提供了三种加载策略，分别为手动加载(Manual)、同步加载(Loading)和异步加载(Asynchronously Loading)
- eviction：驱逐策略。Caffeine提供了三种驱逐策略，分别为基于大小（size-based），基于时间（time-based）和基于引用（reference-based）。
- removal：移除监听器（删除元素时做一些操作）
- refresh：刷新
- writer： 充当底层代理，所有对缓存的读写操作都可以通过writer进行传递。Writer可以把操作缓存和操作外部资源扩展成一个同步的原子性操作。并且在缓存写入完成之前，它将会阻塞后续的更新缓存操作，但是读取（get）将直接返回原有的值。
- statistics：统计信息
- policy：动态修改缓存策略（大小、过期时间等）
- test：测试，即Ticker的使用