package dependencies

import versions.Versions

object SupportDependencies {
    const val core_ktx =  "androidx.core:core-ktx:${Versions.core_ktx_version}"
    const val appcompat =  "androidx.appcompat:appcompat:${Versions.appcompat_version}"
    const val material = "com.google.android.material:material:${Versions.material_version}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_version}"
}