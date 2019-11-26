#### 共同点
- @Resource和@Autowired都可以作为注入属性的修饰，在接口仅有单一实现类时，两个注解的修饰效果相同，可以互相替换，不影响使用。
#### 不同点
- @Resource是Java自己的注解，@Resource有两个属性是比较重要的，分是name和type；Spring将@Resource注解的name属性解析为bean的名字，而type属性则解析为bean的类型。所以如果使用name属性，则使用byName的自动注入策略，而使用type属性时则使用byType自动注入策略。如果既不指定name也不指定type属性，这时将通过反射机制使用byName自动注入策略。
- @Autowired是spring的注解，是spring2.5版本引入的，Autowired只根据type进行注入，不会去匹配name。如果涉及到type无法辨别注入对象时，那需要依赖@Qualifier或@Primary注解一起来修饰。
- Spring属于第三方的，J2EE是Java自己的东西。使用@Resource可以减少代码和Spring之间的耦合。