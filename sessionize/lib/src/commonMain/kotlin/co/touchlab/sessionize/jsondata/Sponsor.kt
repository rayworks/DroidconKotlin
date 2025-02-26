package co.touchlab.sessionize.jsondata

import kotlinx.serialization.Serializable

@Serializable
data class Sponsor(val name: String, val url: String, val icon: String, val sponsorId: String? = null)

@Serializable
data class SponsorGroup(val groupName: String, val sponsors: List<Sponsor>)

