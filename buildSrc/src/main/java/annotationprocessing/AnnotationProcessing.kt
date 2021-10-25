package annotationprocessing

import versions.Versions.glide_version
import versions.Versions.hilt_version
import versions.Versions.room_version

object AnnotationProcessing {

    const val room_compiler = "androidx.room:room-compiler:$room_version"
    const val hilt_compiler = "com.google.dagger:hilt-compiler:$hilt_version"
    const val glide_compiler = "com.github.bumptech.glide:compiler:$glide_version"
}