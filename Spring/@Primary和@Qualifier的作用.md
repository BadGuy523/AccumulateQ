###### 在spring 中使用注解，常使用@Autowired， 默认是根据类型Type来自动注入的。但有些特殊情况，对同一个接口，可能会有几种不同的实现类
- 如有下列一个接口
```
public interface Singer {
    String sing(String lyrics);
}
```
- 有下面两个实现类
```
@Component // 加注解，让spring识别
public class MetalSinger implements Singer{

    @Override
    public String sing(String lyrics) {
        return "I am singing with DIO voice: "+lyrics;
    }
}
//此处无注解，spring无法识别
public class OperaSinger implements Singer {
    @Override
    public String sing(String lyrics) {
        return "I am singing in Bocelli voice: "+lyrics;
    }
}
```
- 若在以下代码中进行注入
```
@Component
 public class SingerService {
        private static final Logger logger = LoggerFactory.getLogger(SingerService.class);
    @Autowired
    private Singer singer;
    public String sing(){
        return singer.sing("song lyrics");
    }
   }
```
- 明显会注入有注解的实现类，若在第二个无注解的实现类也加上@Component注解，则会报错,因为@Autowired根据类型无法选择到底注入哪一个，当然可以换成@Resource注解，加上名称来区分，也可使用以下方式
```
org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type [main.service.Singer] is defined: expected single matching bean but found 2: metalSinger,operaSinger
```
- 方法一：在其中一个实现类上加上@Primary注解，可以让spring在无法区分时优先注入该实现类
```
@Primary
@Component
public class OperaSinger implements Singer{

    @Override
    public String sing(String lyrics) {
        return "I am singing in Bocelli voice: "+lyrics;
    }
}
```
- 方法二：使用@Qualifier注解，需修改两个实现类
```
//修改
@Component // 加注解，让spring识别
@Qualifier("metalSinger")
public class MetalSinger implements Singer{

    @Override
    public String sing(String lyrics) {
        return "I am singing with DIO voice: "+lyrics;
    }
}

@Component
@Qualifier("opreaSinger")
public class OperaSinger implements Singer {
    @Override
    public String sing(String lyrics) {
        return "I am singing in Bocelli voice: "+lyrics;
    }
}
//注入方式
@Component
public class SingerService {
    private static final Logger logger = LoggerFactory.getLogger(SingerService.class);

    @Autowired
    @Qualifier("opreaSinger")
    private Singer singer;

    public String sing(){
        return singer.sing("song lyrics");
    }
}
```
- 拓展
```
@Configuration把一个类作为一个IoC容器，它的某个方法头上如果注册了@Bean，就会作为这个Spring容器中的Bean。
@Scope注解 作用域
@Lazy(true) 表示延迟初始化
@Service用于标注业务层组件、
@Controller用于标注控制层组件（如struts中的action）
@Repository用于标注数据访问组件，即DAO组件。
@Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。
@Scope用于指定scope作用域的（用在类上）
@PostConstruct用于指定初始化方法（用在方法上）
@PreDestory用于指定销毁方法（用在方法上）
@Resource 默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
@DependsOn：定义Bean初始化及销毁时的顺序
@Primary：自动装配时当出现多个Bean候选者时，被注解为@Primary的Bean将作为首选者，否则将抛出异常
@Autowired 默认按类型装配，如果我们想使用按名称装配，可以结合@Qualifier注解一起使用
@Autowired @Qualifier(“personDaoBean”) 存在多个实例配合使用
```
[原文地址](https://blog.csdn.net/qq_16055765/article/details/78833260)