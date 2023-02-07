import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css
import '@/styles/show.css'

import '@/styles/echarts.css'

import App from './App'
import store from './store'
import router from './router'

import * as API from '@/api'
import HintButton from '@/components/HintButton'

import '@/icons' // icon
import '@/permission' // permission control

Vue.prototype.$API = API
Vue.component(HintButton.name, HintButton)

//https://www.cnblogs.com/cnwcl/p/15472445.html
import * as echarts from 'echarts';
Vue.prototype.$echarts = echarts
import 'echarts-wordcloud'
require('echarts-wordcloud')

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
// 将自动注册所有组件为全局组件
import dataV from '@jiaminghi/data-view'
Vue.use(dataV)

Vue.use(ElementUI, { locale })

Vue.config.productionTip = false

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
