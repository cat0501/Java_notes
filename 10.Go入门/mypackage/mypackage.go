// 这个问题意思是你的工作区中可能有多个 go 模块。如果是这种情况，你可以更改 go 扩展设置，以允许 gopls 在工作区中查找多个模块。
// 只需将以下内容添加到您的settings.json：（按ctrl+p，输入setting就能搜到）
package mypackage

import "fmt"

func New(){
	fmt.Println("调用了本地包mypackage并使用它的New方法:mypackage.New")
}