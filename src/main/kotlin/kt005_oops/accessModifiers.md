# Kotlin `Access modifier`

> Trong Kotlin, access modifier quy định cách `class's members` có thể được gọi từ bên ngoài

## Class level modifiers

> Top-level class chỉ có thể là `public` / `internal`  hoặc `private`

## Class members modifers

### Modifiers:

> `public` là default modifer (no-keyword)

> Principle of Least Privilege (nguyên tắc quyền hạn tối thiểu): nếu một biến không cần thay đổi, không nên cấp quyền
> ghi. Khuyến khích `val` (read-only) ở mọi nơi nếu có thể.  
> Ngoài ra, Kotlin cho phép tách biệt quyền đọc và ghi trên cùng 1 biến bằng cách set modifier khác nhau cho get/set.
> => `var` + `private set` // chỉ cho sửa từ bên trong

| Access modifier |                                                            Scope                                                            |
|:---------------:|:---------------------------------------------------------------------------------------------------------------------------:|
|    `private`    | `Chỉ truy cập được bên trong class đó`. Kể cả các sub-class cũng KHÔNG có quyền truy cập vào private member của super-class |
|   `protected`   |                                          `sub-class`, kể cả sub-class ngoài module                                          |
|   `internal`    |                                   `Nội bộ module` -> cùng module + sub-class trong module                                   |
|    `public`     |                                   `KHÔNG có keyword` gì đi kèm, `truy cập từ bất cứ đâu`                                    |

### Overriding:

> Quy tắc overriding: Khi class Con ghi đè hàm của class Cha, quyền truy cập chỉ được giữ nguyên hoặc mở rộng ra, không
> được thu hẹp
> lại.

`private` < `protected` < `internal` < `public`