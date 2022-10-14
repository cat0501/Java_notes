## 全局 API 是什么

Vue 就好比一块蛋糕，生命周期钩子函数以及内部指令可以理解为做蛋糕用的面粉、糖、鸡蛋等。而全局 API 就是裹在蛋糕外面的奶油，让整个蛋糕（Vue）看起来更加美味。

全局 API 的作用就是给 Vue 以更多的自由，大家可以根据自己项目的需求，通过全局 API 来制作出各种各样的方法工具。

## Vue.extend

#### Vue.extend 是什么？

作为全局 API 中的一员，在实际开发中很少会被用到，因为相比我们经常使用的 `Vue.component` ，`Vue.extend` 在写法上就会显得比较繁琐。但在一些比较特殊的场景下，`Vue.extend` + `$mount` 是我们需要去了解的。

#### 自定义纯标签

假设我现在有个需求，在很多地方需要用到我的官网名称，并且这个官网名称还带上 url 地址，可点击跳转到我的官网，在模板中，只需要写一个 `<official/>` 就能展示。

让我们来看看用 `Vue.extend` 怎么去实现，

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Vue.extend-扩展实例构造器</title>
</head>

<body>
<h1>Vue.extend-扩展实例构造器</h1>
<official></official>
<script src="https://cdn.bootcss.com/vue/2.6.10/vue.min.js"></script>
<script type="text/javascript">
    var official = Vue.extend({
        template: "<p><a target='_blank' :href='url'>{{name}}</a></p>",
        data: function () {
            return {
                name: '前端烂笔头',
                url: 'https://zhuanlan.zhihu.com/ChenNick'
            }
        }
    });
    new official().$mount('official');
</script>
</body>
</html>
```



> 全局定义之后，可以在任何地方进行使用，非常便捷。



## Vue.directive 自定义指令

#### Vue.directive 是什么

之前也学了 `v-model`、`v-show` 等官方定义的指令，在项目的开发过程中，我们会有一些特殊的需求，要自定义指令，`Vue.directive` 就是为做这件事情的 API。



#### 自定义组件

假如又来了一个需求，需要让加上 v-color 指令的标签的字体颜色通过传入的变量值进行改变，比如 `v-color="red"` 标签就会变为红色。



```html
<body>
<div id='app'>
    <p v-color="color">天魔缭乱</p>
</div>
</body>

<script src="https://cdn.bootcss.com/vue/2.6.10/vue.min.js"></script>
<script>
    window.onload = function () {
        Vue.directive('color', function (el, binding, vnode) {
            console.log('el', el) // 被绑定的node节点
            console.log('binding', binding) // 一个对象，包含指令的很多信息
            console.log('vnode', vnode) // Vue编译生成的虚拟节点
            el.style = "color:" + binding.value
        });

        new Vue({
            el: '#app',
            data: {
                color: 'red'
            }
        })
    }
</script>
```



#### 回调函数参数

自定义组件的回调函数有三个参数。

- el：被绑定的 node 节点。
- binding： 包含指令信息的对象参数。
- vnode：Vue 编译生成的虚拟节点。



#### 自定义组件的生命周期

自定义组件包含几个生命周期，也就是在调用自定义组件时，几个钩子函数会被触发，分别是如下。

- bind：只会调用一次，在第一次绑定到元素上时被调用，初始化操作可以使用它。
- inserted：被绑定的元素插入了父节点。
- update：被绑定的元素模板更新时调用。
- componentUpdated：被绑定的元素模板完成一次生命周期。
- unbind：指令和被绑定元素解绑时调用。



## Vue.set 全局操作

在解释 `Vue.set` 之前先了解一下 Vue 的响应式原理：

> 当你把一个 JS 对象传给 Vue 实例的 data 属性时，Vue 将遍历此对象的所有属性，并使用 Object.defineProperty 把这些属性全部转为 getter/setter。Object.defineProperty 是 ES5 中一个无法 shim 的特性，这也就是为什么 Vue 不支持 IE8 以及更低版本的浏览器。



#### 为什么要使用 Vue.set

受限于现代浏览器，Vue 检测不到对象的添加和删除；因为 Vue 在初始化实例时对 `data` 属性执行 `getter/setter` 转化操作，所以对象必须在 `data` 中才能让其响应式。

Vue 不允许在已经创建的实例上动态添加新的根级响应式属性，不过可以使用 `Vue.set` 方法将响应式属性添加到嵌套的对象上。























