package com.prodevzla.pokedex.domain.usecase

import android.net.Uri
import com.prodevzla.pokedex.domain.repository.AudioRepository

class PlayMPAudioUseCase(
    private val audioRepository: AudioRepository
) {

    operator fun invoke(audio: Uri?) {
        if (audio == null) {
            audioRepository.stopAudioMP()
            return
        }
        audioRepository.playAudioMP(audio)
    }

}
