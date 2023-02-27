import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bootcamp.lutfi_mealdb.data.LocalDataSource
import com.bootcamp.lutfi_mealdb.data.RemoteDataSource
import com.bootcamp.lutfi_mealdb.data.Repository
import com.bootcamp.lutfi_mealdb.data.database.Meal
import com.bootcamp.lutfi_mealdb.data.database.MealEntity
import com.bootcamp.lutfi_mealdb.data.network.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteDetailViewModel(application: Application): AndroidViewModel(application) {
    // API
    private val mealService = Service.MealService
    private val remote = RemoteDataSource(mealService)

    // LOCAL
    private val mealDao = Meal.getDatabase(application).MealDao()
    private val local = LocalDataSource(mealDao)
    private val repository = Repository(remote,local)

    fun deleteFavoriteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(mealEntity)
        }
    }

}