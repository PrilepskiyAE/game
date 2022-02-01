package com.ambrella.game.domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
data class GameResult(
    val winner:Boolean,
    val countOfRightAnswers:Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
        ):Parcelable