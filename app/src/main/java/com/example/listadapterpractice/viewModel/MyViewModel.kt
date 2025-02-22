package com.example.listadapterpractice.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listadapterpractice.model.User
import com.example.listadapterpractice.repository.GetUserRepository
import com.example.listadapterpractice.repository.InsertUserRepository
import com.example.listadapterpractice.repository.UpdateUserRepository
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

internal class MyViewModel(
    private val insertUserRepository: InsertUserRepository,
    private val getUserRepository: GetUserRepository,
    private val updateUserRepository: UpdateUserRepository
) : ViewModel() {
    private val _userList: LiveData<List<User>>
    val usersList: LiveData<List<User>>
        get() {
            return _userList
        }

    init {
        _userList = getUserRepository.getUser()
    }

    // todo : 이 방식이 제대로된 subMitList를 쓰는 방법인 것 같다.
    fun update(pos: Int, user: User) {
        val newList = mutableListOf<User>()
        _userList.value?.let {
            newList.addAll(it)
        }
        newList.set(pos, user)
        //_userList.value = newList
    }

    var clickedPosition : Int = 0


    fun insertUser(userName: String) = viewModelScope.launch {
        val user = User(userName)
        insertUserRepository.insertUser(user)
    }

    fun updateUser(userName: String, user: User) = viewModelScope.launch {
        user.name = userName
        updateUserRepository.updateUser(user)
    }

}