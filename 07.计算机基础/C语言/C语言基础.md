



# 环境配置

- VSCode
- DevC++







# 基础知识



## 函数

函数原型：`int printf(const char *format, ...);`



格式化字符串中的常见转义序列：

- `%d`：输出一个`int`类型的变量。
- `%f`：输出一个`float`或`double`类型的变量。
- `%c`：输出一个字符。
- `%s`：输出一个字符串。
- `%x`：以十六进制格式输出一个整数变量。



```c
int age = 25;
float salary = 10000.50;
char gender = 'M';
char name[] = "John Smith";

printf("姓名：%s\n", name);
printf("年龄：%d\n", age);
printf("薪水：%.2f\n", salary);
printf("性别：%c\n", gender);
printf("年龄的十六进制表示：%x\n", age);

姓名：John Smith
年龄：25
薪水：10000.50
性别：M
年龄的十六进制表示：19
```







累加求和

```c
#include <stdio.h>

int main() {
    int i;
    int sum = 0;

    // printf("输入一个整数：");
    // scanf("%d", &n);

    for (i = 1; i <= 10; i++) {
        sum += i;
    }

    printf("f(%d)=%d\n", 10, sum); // 使用常量 10 作为参数传递给 printf 函数

    return 0;
}
```























# 参考

- Windows/macOS使用VSCode搭建C/C++的开发/Debug环境：https://zhuanlan.zhihu.com/p/571934657