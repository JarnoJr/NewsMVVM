package dependencies

import versions.Versions
import versions.Versions.coroutines_version
import versions.Versions.fragment_version
import versions.Versions.hilt_view_model_version
import versions.Versions.interceptor_version
import versions.Versions.nav_version
import versions.Versions.retro_version
import versions.Versions.swipe_refresh_version
import versions.Versions.timber_version


object Dependencies {
    //Navigation Component
    const val navigation_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:$nav_version"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:$nav_version"

    //fragment-ktx
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${fragment_version}"

    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:$retro_version"
    const val gson_converter = "com.squareup.retrofit2:converter-gson:$retro_version"
    const val http_interceptor = "com.squareup.okhttp3:logging-interceptor:$interceptor_version"

    //paging
    const val paging = "androidx.paging:paging-runtime:${Versions.paging_version}"

    //dagger-hilt
    const val dagger_hilt = "com.google.dagger:hilt-android:${Versions.hilt_version}"
    const val hilt_view_model = "androidx.hilt:hilt-lifecycle-viewmodel:$hilt_view_model_version"

    //glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"


    const val swipe_refresh = "androidx.swiperefreshlayout:swiperefreshlayout:$swipe_refresh_version"

    //room
    const val room = "androidx.room:room-runtime:${Versions.room_version}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room_version}"

    // timber
    const val timber = "com.jakewharton.timber:timber:$timber_version"

    //coroutines
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"

    //datastore
    const val data_store = "androidx.datastore:datastore-preferences:${Versions.datastore_version}"
}