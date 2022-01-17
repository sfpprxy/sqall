# SQalL

## Quick start


```java
// Query from local elasticsearch
public static void main(String[] args) {
    SQall client = new SQElasticsearch();
    client.connect("127.0.0.1");
    client.query("SELECT * FROM customer WHERE age < 41")
            .get()
            .asTable()
            .prettyPrint();
}

// Console result
+----+----------+---------+------+---------+
| id | name     | sex     | age  | country |
+----+----------+---------+------+---------+
| 1  | Anders   | male    | 33   | USA     |
| 2  | Chen     | female  | 16   | CN      |
| 3  | Wang     | male    | 25   | CN      |
| 4  | Alfreds  | male    | 40   | USA     |
| 5  | Zhou     | female  | 21   | CN      |
+----+----------+---------+------+---------+

```
