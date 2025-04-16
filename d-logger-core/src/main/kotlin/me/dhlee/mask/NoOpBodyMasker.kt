package me.dhlee.mask

class NoOpBodyMasker: BodyMasker {
    override fun mask(body: String?): String = body.orEmpty()
}