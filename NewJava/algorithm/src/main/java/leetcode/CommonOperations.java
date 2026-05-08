package leetcode;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Java 常用函数式接口 (Functional Interface) 典型场景示例。
 * 函数式接口 = 只有一个抽象方法的接口，可以用 Lambda 或方法引用实现。
 */
public class CommonOperations {

    // ==================== Function<T, R> : 转换 ====================

    static class User {
        String name;
        String email;
        int age;
        User(String name, String email, int age) {
            this.name = name; this.email = email; this.age = age;
        }
        public String getEmail() { return email; }
        public String getName() { return name; }
        public int getAge() { return age; }
    }

    static class UserVO {
        String displayName;
        String contact;
        UserVO(String displayName, String contact) {
            this.displayName = displayName; this.contact = contact;
        }
    }

    // 实际场景: 从一批用户中提取域名, 去重后统计有哪些邮件域名
    public void functionExtractDomain() {
        List<User> users = List.of(
                new User("a", "alice@google.com", 25),
                new User("b", "bob@gmail.com", 30),
                new User("c", "charlie@google.com", 28)
        );

        // Function<User, String>: 从 user 对象提取域名
        // map 的入参就是 Function<? super T, ? extends R>
        Set<String> domains = users.stream()
                .map(u -> u.getEmail())                          // Function: User → String (email)
                .map(email -> email.substring(email.indexOf("@") + 1))  // Function: String → String (domain)
                .collect(Collectors.toSet());
        // -> {google.com, gmail.com}

        // Function 也可以用在 computeIfAbsent
        Map<String, List<User>> domainIndex = new HashMap<>();
        for (User u : users) {
            String domain = u.getEmail().substring(u.getEmail().indexOf("@") + 1);
            // Function<String, List<User>>: key(domain) → 初始值(空list)
            domainIndex.computeIfAbsent(domain, k -> new ArrayList<>()).add(u);
        }
    }

    // Function 常用于 DTO → Entity 转换
    public UserVO toVO(User u) {
        return new UserVO(u.getName(), u.getEmail());
    }

    public void functionDtoConversion() {
        List<User> users = List.of(
                new User("a", "alice@google.com", 25),
                new User("b", "bob@gmail.com", 30)
        );
        // 方法引用: User → UserVO
        List<UserVO> vos = users.stream().map(this::toVO).toList();
    }

    // ==================== Consumer<T> : 消费 ====================

    static class Order {
        String id;
        String userId;
        double amount;
        Order(String id, String userId, double amount) {
            this.id = id; this.userId = userId; this.amount = amount;
        }
    }

    // 实际场景: 批量处理订单 → 发通知 + 记录日志
    public void consumerBatchProcess() {
        List<Order> orders = List.of(
                new Order("O001", "u1", 100),
                new Order("O002", "u2", 200)
        );

        // Consumer: 每个订单发通知
        Consumer<Order> notify = order ->
                System.out.println("通知用户 " + order.userId + ": 订单 " + order.id + " 已创建");

        // Consumer: 每个订单记录审计日志
        Consumer<Order> auditLog = order ->
                System.out.println("[AUDIT] " + LocalDateTime.now() + " 订单=" + order.id);

        // andThen: 把两个 Consumer 串联, 先通知再记日志
        orders.forEach(notify.andThen(auditLog));
    }

    // BiConsumer: 遍历 Map
    public void biConsumerMapIterate() {
        Map<String, Double> scoreMap = new HashMap<>();
        scoreMap.put("math", 90.5);
        scoreMap.put("english", 85.0);

        // BiConsumer<String, Double>: (科目, 分数) → 打印
        scoreMap.forEach((subject, score) ->
                System.out.println(subject + "=" + score));

        // 计算总分
        double[] total = {0};
        scoreMap.forEach((k, v) -> total[0] += v);
    }

    // ==================== Supplier<T> : 供应 ====================

    // 实际场景: 从数据库查配置, 带缓存; 只在缓存未命中时才查
    private Map<String, String> configCache = new HashMap<>();

    public String getConfig(String key, Supplier<String> dbLoader) {
        String val = configCache.get(key);
        if (val == null) {
            // Supplier: 只在需要时才执行, 避免不必要的 DB 查询
            val = dbLoader.get();
            configCache.put(key, val);
        }
        return val;
    }

    // 另一个场景: 重试机制
    public <T> T retry(Supplier<T> action, int maxRetries) {
        for (int i = 0; i < maxRetries; i++) {
            try {
                return action.get();  // Supplier: 封装一段可能失败的操作
            } catch (Exception e) {
                if (i == maxRetries - 1) throw e;
            }
        }
        return null;
    }

    public void supplierExample() {
        // 用 Optional.orElseGet: 只在值为空时创建默认值
        Optional<String> opt = Optional.empty();
        String result = opt.orElseGet(() -> {
            System.out.println("实际执行了 supplier, 因为值是空的");
            return "默认值";
        });
    }

    // ==================== Predicate<T> : 判断 ====================

    // 实际场景: 多条件过滤用户列表
    public void predicateFilterUsers() {
        List<User> users = List.of(
                new User("a", "alice@google.com", 17),
                new User("b", "bob@gmail.com", 30),
                new User("c", "charlie@google.com", 28)
        );

        Predicate<User> adult = u -> u.getAge() >= 18;
        Predicate<User> fromGoogle = u -> u.getEmail().contains("@google.com");

        // 组合条件: 成年人 且 Google 用户
        List<User> adultGoogleUsers = users.stream()
                .filter(adult.and(fromGoogle))
                .toList();

        // removeIf: 移除未成年用户
        List<User> mutable = new ArrayList<>(users);
        mutable.removeIf(u -> u.getAge() < 18);
    }

    // ==================== Map 核心方法实战 ====================

    // ---- 场景 1: CountString —— 分组记录索引 ----
    public Map<String, List<Integer>> countString(String[] words) {
        Map<String, List<Integer>> indexMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            // computeIfAbsent: key不存在才创建新list
            // 和 putIfAbsent 的区别:
            //   putIfAbsent: 每次都 new ArrayList<>(), 不管key在不在
            //   computeIfAbsent: 只有key不在时才执行 lambda (惰性, 效率更高)
            indexMap.computeIfAbsent(w, k -> new ArrayList<>()).add(i);
        }
        return indexMap;
    }

    // ---- 场景 2: 词频统计 —— 用 merge ----
    public Map<String, Integer> wordCount(String[] words) {
        Map<String, Integer> count = new HashMap<>();
        for (String w : words) {
            // merge: key不存在 → put(1), 存在 → old + 1
            // 语义简洁: "统计单词出现次数, 每次加1"
            count.merge(w, 1, Integer::sum);
        }
        // 等价于:
        //   count.put(w, count.getOrDefault(w, 0) + 1);
        // merge 一行, 不用显式 get + put
        return count;
    }

    // ---- 场景 3: 购物车 —— 用 merge 合并商品 ----
    static class CartItem {
        String sku;
        int quantity;
        double price;
        CartItem(String sku, int quantity, double price) {
            this.sku = sku; this.quantity = quantity; this.price = price;
        }
    }

    public Map<String, Integer> mergeCart(List<CartItem> items) {
        Map<String, Integer> cart = new HashMap<>();
        for (CartItem item : items) {
            // merge: 同一个 sku 累加数量
            cart.merge(item.sku, item.quantity, Integer::sum);
        }
        return cart;
    }

    // ---- 场景 4: 多字段索引 (图: node -> [neighbors]) ----
    public Map<String, List<String>> buildGraph(List<String[]> edges) {
        Map<String, List<String>> graph = new HashMap<>();
        for (String[] edge : edges) {
            String from = edge[0], to = edge[1];
            // 双向: from → to, to → from
            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            graph.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
        }
        return graph;
    }
    // -> {"A"=[B, C], "B"=[A], "C"=[A]}

    // ---- 场景 5: 缓存更新 —— 用 compute 或 computeIfPresent ----
    static class CacheEntry {
        Object value;
        LocalDateTime expireAt;
        CacheEntry(Object value, int ttlSeconds) {
            this.value = value;
            this.expireAt = LocalDateTime.now().plusSeconds(ttlSeconds);
        }
    }

    Map<String, CacheEntry> cache = new HashMap<>();

    // 定期清理过期缓存: 如果key存在且过期了, 返回null(删除)
    public void evictExpired() {
        cache.forEach((key, entry) -> {
            cache.computeIfPresent(key, (k, v) ->
                    v.expireAt.isBefore(LocalDateTime.now()) ? null : v
            );
            // 返回 null → map 会 remove 掉这个 key
        });
    }

    // ---- 场景 6: compute 处理 key 在/不在两种逻辑 ----
    // 语义: 不管 key 在不在, 都由你决定 value 是什么
    public void computeUpsert() {
        Map<String, List<Integer>> map = new HashMap<>();
        // 第一次 key="a" 不存在: v == null → 创建新list并加1
        // 第二次 key="a" 存在: v 是旧list → 追加2
        map.compute("a", (k, v) -> {
            if (v == null) {
                List<Integer> l = new ArrayList<>();
                l.add(1);
                return l;       // 返回非null → put 进 map
            } else {
                v.add(2);
                return v;       // 返回旧list → 更新
            }
        });
    }

    // ---- 场景 7: computeIfPresent 更新登录时间 ----
    static class Session {
        String token;
        LocalDateTime lastAccess;
        Session(String token) {
            this.token = token;
            this.lastAccess = LocalDateTime.now();
        }
    }

    Map<String, Session> sessionStore = new HashMap<>();

    public void refreshSession(String token) {
        // computeIfPresent: 只在 token 还在时更新时间, 不在就什么都不做
        sessionStore.computeIfPresent(token, (k, sess) -> {
            sess.lastAccess = LocalDateTime.now();
            return sess;   // 返回非null → 更新回map
        });
    }

    // ==================== BinaryOperator / BiFunction : 合并/归约 ====================

    // BinaryOperator: 两个同类型合并成一个
    public void binaryOperatorMerge() {
        Map<String, Integer> sales2023 = new HashMap<>();
        sales2023.put("A", 100);
        sales2023.put("B", 200);

        Map<String, Integer> sales2024 = new HashMap<>();
        sales2024.put("B", 300);
        sales2024.put("C", 400);

        // 合并两年的销售额, 相同key用 BinaryOperator 累加
        Map<String, Integer> total = new HashMap<>(sales2023);
        sales2024.forEach((k, v) ->
                total.merge(k, v, Integer::sum)
        );
        // -> {A=100, B=500, C=400}
    }

    // BiFunction: 把两个不同类型的值组合成新值
    public void biFunctionCombine() {
        List<User> users = List.of(
                new User("a", "alice@google.com", 25),
                new User("b", "bob@gmail.com", 30)
        );

        // 只保留成年人 (Predicate), 然后组装成一句话 (Function)
        List<String> descriptions = users.stream()
                .filter(u -> u.getAge() >= 18)
                .map(u -> u.getName() + "(" + u.getAge() + "岁)")
                .toList();

        // reduce: BinaryOperator — 两两合并
        String result = descriptions.stream()
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        // -> "a(25岁), b(30岁)"
    }

    // ==================== Stream 综合实战 ====================

    // 实际: 从订单列表统计每个用户的消费总额, 只保留消费 > 100 的
    public void streamRealExample() {
        List<Order> orders = List.of(
                new Order("O001", "u1", 100),
                new Order("O002", "u1", 50),
                new Order("O003", "u2", 300),
                new Order("O004", "u3", 80)
        );

        Map<String, Double> userTotal = orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.userId,                          // Function: Order → String (分组key)
                        Collectors.summingDouble(o -> o.amount) // 组内求和
                ));

        // 再过滤消费 <= 100 的用户 (Predicate)
        Map<String, Double> filtered = userTotal.entrySet().stream()
                .filter(e -> e.getValue() > 100)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // -> {u1=150.0, u2=300.0}
    }

}
