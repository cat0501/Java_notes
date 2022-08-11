package main

import (
	"fmt"
	"mypackage"  // 导入同一项目下的mypackage包
)

func main() {
	mypackage.New()
	fmt.Println("main")
}

// main.go:5:2: package mypackage is not in GOROOT (/usr/local/go/src/mypackage)
// 解决办法：go env -w GO111MODULE="auto"



