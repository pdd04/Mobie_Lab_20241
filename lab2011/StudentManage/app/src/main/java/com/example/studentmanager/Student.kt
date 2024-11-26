package com.example.studentmanager

import java.io.Serializable

data class Student(
    var name: String,
    var studentId: String
) : Serializable

