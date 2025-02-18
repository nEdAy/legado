package io.legado.app.lib.webdav

import io.legado.app.data.appDb
import io.legado.app.exception.NoStackTraceException
import okhttp3.Credentials
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

data class Authorization(
    val username: String,
    val password: String,
    val charset: Charset = StandardCharsets.ISO_8859_1
) {

    var name = "Authorization"
        private set

    var data: String = Credentials.basic(username, password, charset)
        private set

    override fun toString(): String {
        return "$username:$password"
    }

    constructor(serverID: Long?): this("","") {
        serverID ?: throw NoStackTraceException("Unexpected server ID")
        appDb.serverDao.get(serverID)?.getWebDavConfig().run {
            data = Credentials.basic(username, password, charset)
        } ?: throw WebDavException("Unexpected WebDav Authorization")
    }

}