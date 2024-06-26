package com.example.brushuptodolist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableAspectJAutoProxy
@SpringBootApplication
class BrushUpTodoListApplication

fun main(args: Array<String>) {
    runApplication<BrushUpTodoListApplication>(*args)
}
