/*
 * Copyright 2020 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/master/LICENSE
 */

package net.mamoe.mirai.qqandroid.message

import net.mamoe.mirai.message.data.OfflineFriendImage
import net.mamoe.mirai.message.data.OfflineGroupImage
import net.mamoe.mirai.message.data.OnlineFriendImage
import net.mamoe.mirai.message.data.OnlineGroupImage
import net.mamoe.mirai.qqandroid.network.protocol.data.proto.ImMsgBody
import net.mamoe.mirai.utils.ExternalImage
import net.mamoe.mirai.utils.io.hexToBytes


internal class OnlineGroupImageImpl(
    internal val delegate: ImMsgBody.CustomFace
) : OnlineGroupImage() {
    override val filepath: String = delegate.filePath
    override val fileId: Int get() = delegate.fileId
    override val serverIp: Int get() = delegate.serverIp
    override val serverPort: Int get() = delegate.serverPort
    override val fileType: Int get() = delegate.fileType
    override val signature: ByteArray get() = delegate.signature
    override val useful: Int get() = delegate.useful
    override val md5: ByteArray get() = delegate.md5
    override val bizType: Int get() = delegate.bizType
    override val imageType: Int get() = delegate.imageType
    override val width: Int get() = delegate.width
    override val height: Int get() = delegate.height
    override val source: Int get() = delegate.source
    override val size: Int get() = delegate.size
    override val original: Int get() = delegate.origin
    override val pbReserve: ByteArray get() = delegate.pbReserve
    override val imageId: String = ExternalImage.generateImageId(delegate.md5, imageType)
    override val originUrl: String
        get() = "http://gchat.qpic.cn" + delegate.origUrl

    override fun equals(other: Any?): Boolean {
        return other is OnlineGroupImageImpl && other.filepath == this.filepath && other.md5.contentEquals(this.md5)
    }

    override fun hashCode(): Int {
        return imageId.hashCode() + 31 * md5.hashCode()
    }
}

internal class OnlineFriendImageImpl(
    internal val delegate: ImMsgBody.NotOnlineImage
) : OnlineFriendImage() {
    override val resourceId: String get() = delegate.resId
    override val md5: ByteArray get() = delegate.picMd5
    override val filepath: String get() = delegate.filePath
    override val fileLength: Int get() = delegate.fileLen
    override val height: Int get() = delegate.picHeight
    override val width: Int get() = delegate.picWidth
    override val bizType: Int get() = delegate.bizType
    override val imageType: Int get() = delegate.imgType
    override val downloadPath: String get() = delegate.downloadPath
    override val fileId: Int get() = delegate.fileId
    override val original: Int get() = delegate.original
    override val originUrl: String
        get() = "http://c2cpicdw.qpic.cn" + this.delegate.origUrl

    override fun equals(other: Any?): Boolean {
        return other is OnlineFriendImageImpl && other.resourceId == this.resourceId && other.md5
            .contentEquals(this.md5)
    }

    override fun hashCode(): Int {
        return imageId.hashCode() + 31 * md5.hashCode()
    }
}

internal fun OfflineGroupImage.toJceData(): ImMsgBody.CustomFace {
    return ImMsgBody.CustomFace(
        filePath = this.filepath,
        fileId = this.fileId,
        serverIp = this.serverIp,
        serverPort = this.serverPort,
        fileType = this.fileType,
        signature = this.signature,
        useful = this.useful,
        md5 = this.md5,
        bizType = this.bizType,
        imageType = this.imageType,
        width = this.width,
        height = this.height,
        source = this.source,
        size = this.size,
        origin = this.original,
        pbReserve = this.pbReserve,
        flag = ByteArray(4),
        //_400Height = 235,
        //_400Url = "/gchatpic_new/1040400290/1041235568-2195821338-01E9451B70EDEAE3B37C101F1EEBF5B5/400?term=2",
        //_400Width = 351,
        oldData = oldData
    )
}

private val oldData: ByteArray =
    "15 36 20 39 32 6B 41 31 00 38 37 32 66 30 36 36 30 33 61 65 31 30 33 62 37 20 20 20 20 20 20 35 30 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 7B 30 31 45 39 34 35 31 42 2D 37 30 45 44 2D 45 41 45 33 2D 42 33 37 43 2D 31 30 31 46 31 45 45 42 46 35 42 35 7D 2E 70 6E 67 41".hexToBytes()


internal fun OfflineFriendImage.toJceData(): ImMsgBody.NotOnlineImage {
    return ImMsgBody.NotOnlineImage(
        filePath = this.filepath,
        resId = this.resourceId,
        oldPicMd5 = false,
        picMd5 = this.md5,
        fileLen = this.fileLength,
        picHeight = this.height,
        picWidth = this.width,
        bizType = this.bizType,
        imgType = this.imageType,
        downloadPath = this.downloadPath,
        original = this.original,
        fileId = this.fileId,
        pbReserve = byteArrayOf(0x78, 0x02)
    )
}