package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.repository.AudioRepository

class PlayMPAudioUseCase(
    private val audioRepository: AudioRepository
) {

    operator fun invoke(audio: String?) {
        audioRepository.toggleMPlayer(audio)
    }

}
