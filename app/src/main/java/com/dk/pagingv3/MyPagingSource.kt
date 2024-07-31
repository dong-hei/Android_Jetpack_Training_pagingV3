import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dk.pagingv3.data.GithubItems
import com.dk.pagingv3.network.GithubApi
import kotlinx.coroutines.delay

private const val STATING_KEY = 1
// private const val STATING_KEY = 5

class MyPagingSource (
    private val githubApi: GithubApi
) : PagingSource<Int, GithubItems>(){


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubItems> {

        val page = params.key ?: STATING_KEY

        val response = githubApi.getData("android", page, params.loadSize)

        val data = response.items

        if(page != 1) {
            delay(2000)
        }

        if(data == null) {
            return LoadResult.Page(
                data = listOf(),
                prevKey = null,
                nextKey = null
            )
        } else {
            return LoadResult.Page(
                data = data,
                prevKey = if(page == 1) null else page-1,
                nextKey = page + (params.loadSize / 30)
            )
        }

    }

    override fun getRefreshKey(state: PagingState<Int, GithubItems>): Int? {

        Log.d("getRefreshKey", "start")

        // android paging 라이브러리에서 제공하는 기능
        // 적당히 보고 있는 페이지 position 을 가져와서

        val anchorPosition = state.anchorPosition


        // 적당히 보고 있는 페이지를 찾아서 데이터를 받아옴
        // 페이지를 return

        return anchorPosition?.let { anchorPosition

            // prevKey -> null -> 첫번째 페이지
            // nextKey -> null -> 마지막 페이지

            Log.d("anchorPosition", anchorPosition.toString())

            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }

}