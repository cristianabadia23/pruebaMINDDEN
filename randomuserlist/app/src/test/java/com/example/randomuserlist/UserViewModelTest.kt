package com.example.randomuserlist

import android.app.Application
import androidx.lifecycle.Observer
import com.example.randomuserlist.models.User
import com.example.randomuserlist.models.UsersResponse
import com.example.randomuserlist.models.BirthDate
import com.example.randomuserlist.models.Coordinates
import com.example.randomuserlist.models.Login
import com.example.randomuserlist.models.Name
import com.example.randomuserlist.models.Picture
import com.example.randomuserlist.models.Register
import com.example.randomuserlist.models.Street
import com.example.randomuserlist.viewmodel.UserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.net.UnknownHostException
import com.example.randomuserlist.interfaces.ApiServiceable
import com.example.randomuserlist.models.ID
import com.example.randomuserlist.models.Location
import com.example.randomuserlist.models.Timezone

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @Mock
    private lateinit var apiService: ApiServiceable

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var userListObserver: Observer<List<User>>

    @Mock
    private lateinit var toastMessageObserver: Observer<String>

    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        viewModel = UserViewModel(application)
        viewModel.toastMessage.observeForever(toastMessageObserver)
    }

    @Test
    fun `loadMoreUsers success`() = runBlocking {
        val response = UsersResponse(results = setOf(User(gender = "Male", name = Name("Mr", "John", "Doe"), location = Location(
            street = Street("123", "Main Street"),
            city = "City",
            state = "State",
            country = "Country",
            postcode = "12345",
            coordinates = Coordinates("12.345", "67.890"),
            timezone = Timezone("UTF+8","GMT+1")
        ), email = "john.doe@example.com", login = Login(
            uuid = "123",
            username = "john_doe",
            password = "password",
            salt = "salt",
            md5 = "md5",
            sha1 = "sha1",
            sha256 = "sha256"
        ), dob = BirthDate("1990-01-01", "32"), registered = Register("2020-01-01", "2"), phone = "1234567890", cell = "9876543210", id = ID(
            name = "IDName",
            value = "IDValue"
        ), picture = Picture(
            large = "url_large",
            medium = "url_medium",
            thumbnail = "url_thumbnail"
        ), nat = "US")))

        `when`(apiService.getUserByResults(page = 1, results = 10)).thenReturn(response)

        viewModel.loadMoreUsers()

        verify(userListObserver).onChanged(response.results.toList())
        verify(toastMessageObserver, never()).onChanged(any())
    }


    @Test
    fun `loadMoreUsers error`() = runBlocking {
        `when`(apiService.getUserByResults(page = 1, results = 10)).thenThrow(UnknownHostException())

        viewModel.loadMoreUsers()

        verify(userListObserver, never()).onChanged(any())
        verify(toastMessageObserver).onChanged("Error al conectarse con la web, vuelva a intentarlo más tarde")
    }
}
