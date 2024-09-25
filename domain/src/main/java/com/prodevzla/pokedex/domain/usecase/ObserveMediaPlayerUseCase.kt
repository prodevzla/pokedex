package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import com.prodevzla.pokedex.domain.repository.AudioRepository
import kotlinx.coroutines.flow.Flow

class ObserveMediaPlayerUseCase(
    private val audioRepository: AudioRepository
) {

    operator fun invoke(): Flow<AudioPlaybackState> = audioRepository.observeMediaPlayerState()

}
