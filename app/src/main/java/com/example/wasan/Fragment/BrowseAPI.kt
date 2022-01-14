package com.example.wasan.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wasan.Database.TvShowDB
import com.example.wasan.Model.TvShow
import com.example.wasan.R
import com.example.wasan.RecyclerView.APIAdapter
import kotlinx.android.synthetic.main.fragment_browse_a_p_i.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL


class BrowseAPI : Fragment() {

    private lateinit var apiAdapter: APIAdapter

    private lateinit var shows: MutableList<TvShow>

    private val database by lazy { TvShowDB.getInstance(requireContext()).tvShowDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_browse_a_p_i, container, false)
        shows = mutableListOf()

        apiAdapter = APIAdapter(activity?.applicationContext)
        view.rvApi.adapter = apiAdapter
        view.rvApi.layoutManager = LinearLayoutManager(activity)

        view.btnSearch.setOnClickListener {
            if (view.etSearch.text.isNotEmpty()) {

                buildURL(view.etSearch.text.toString())
                apiAdapter.update(shows)
                shows.clear()
            }
        }
        return view
    }

    private fun buildURL(showName: String) {

        CoroutineScope(Dispatchers.IO).launch {
            var myResponse: String = ""
            myResponse = try {
                URL("https://api.tvmaze.com/search/shows?q=$showName").readText(Charsets.UTF_8)
            } catch (e: Exception) {
                ""
            }

            if (myResponse.isNotEmpty()) {
                fetchData(myResponse)
            }
        }
    }

    private suspend fun fetchData(response: String) {
        withContext(Main) {
            try {
                val json = JSONArray(response)
                for (i in 0 until json.length()) {
                    val myObject = json.getJSONObject(i)
                    val show = myObject.getJSONObject("show")
                    val id = show.getInt("id")
                    val url = show.getString("url")
                    val name = show.getString("name")
                    val summary = show.getString("summary")
                    val genres = show.getJSONArray("genres")
                    var genresString = ""
                    for (g in 0 until genres.length()) {
                        genresString += "${genres.getString(g)}, "
                    }
                    val lang = show.getString("language")
                    val officialSite = show.getString("officialSite")

                    val premiered = show.getString("premiered")
                    val ended = show.getString("ended")
                    val image = try {
                        show.getJSONObject("image").getString("medium")
                    } catch (e: Exception) {
                        ""
                    }
                    val rating = show.getJSONObject("rating").getString("average")
                    shows.add(
                        TvShow(
                            id,
                            url,
                            name,
                            genresString,
                            lang,
                            officialSite,
                            "",
                            "",
                            rating,
                            image,
                            premiered,
                            ended,
                            summary
                        )
                    )

                }

            } catch (e: Exception) {
            }
        }
    }
}