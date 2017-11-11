/*
 *  Copyright (C) 2017 Darel Bitsy
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.dbeginc.dbweatherdomain.usecases.configurations

import com.dbeginc.dbweatherdomain.repositories.configurations.ConfigurationsRepository
import com.dbeginc.dbweatherdomain.usecases.UseCaseSingle
import io.reactivex.Single

/**
 * Created by darel on 26.10.17.
 *
 * Get Weather Notification status
 */
class GetWeatherNotificationStatus(private val configurationsRepository: ConfigurationsRepository) : UseCaseSingle<Boolean, Unit>() {

    override fun buildUseCase(params: Unit): Single<Boolean> = configurationsRepository.getWeatherNotificationStatus()

    override fun clean() = configurationsRepository.clean()
}