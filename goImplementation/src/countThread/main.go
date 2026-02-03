package main

import (
	"fmt"
	"sync"
)

var wg sync.WaitGroup

func count(a int, b int) {
	defer wg.Done()
	for i := a; i <= b; i++ {
		fmt.Println(i)
	}
}

func main() {
	//Cantidad de GoRoutines
	wg.Add(3)
	go count(0, 99)
	go count(100, 199)
	go count(200, 299)
	wg.Wait()
}
