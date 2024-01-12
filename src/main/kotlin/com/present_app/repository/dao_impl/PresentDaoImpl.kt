package com.present_app.repository.dao_impl

import com.present_app.repository.dao.PresentDao
import com.present_app.repository.entity.Present
import com.present_app.repository.entity.Stage
import java.sql.Connection

class PresentDaoImpl(private val connection: Connection) : PresentDao {

    override fun getLastItemBySenderId(id: Int): Present? {
        return try {
            val result = connection.createStatement().executeQuery(
                        "SELECT id_present, \"text\", id_sender, present_img, \"key\", redirect_url " +
                                "FROM public.present ORDER BY id_present DESC LIMIT 1"
            )
            if (result.next()) {
                val idPresent = result.getInt(1)
                val text = result.getString(2)
                val idSender = result.getInt(3)
                val image = result.getString(4)
                val key = result.getString(5)
                val link = result.getString(6)
                return Present(id = idPresent, text = text, idSender = idSender, image = image, keyOpen = key, link = link)
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    override fun getPresentsListByStages(stages: List<Stage>): List<Present>? {
        return try {
            val presents: MutableList<Present> = mutableListOf()
            for (i in stages.indices) {
                val result = connection.createStatement().executeQuery("SELECT * FROM public.present WHERE id_present = ${stages[i].idPresent}")
                if (result.next()) {
                    val id = result.getInt(1)
                    val text = result.getString(2)
                    val idSender = result.getInt(3)
                    val image = result.getString(4)
                    val keyOpen = result.getString(5)
                    val link = result.getString(6)

                    val present = Present(id, text, idSender, image, keyOpen, link)
                    presents.add(present)
                }
            }
            presents
        } catch (_: Exception) {
            null
        }
    }

    override fun updateImage(image: String, idPresent: Int) {
        try {
            connection.createStatement().executeQuery(
                "UPDATE present SET present_img = '$image' WHERE idPresent = $idPresent"
            )
        } catch (_: Exception) {
        }
    }

    override fun create(item: Present): Boolean {
        return try {
            connection.createStatement().executeQuery(
                "INSERT INTO public.present (\"text\", id_sender, present_img, \"key\", redirect_url) " +
                        "VALUES ( '${item.text}', ${item.idSender}, '${item.image}', '${item.keyOpen}', '${item.link}' )"
            )
            false
        } catch (_: Exception) {
            true
        }

    }

    override fun getItem(idPresent: Int): Present? {
        return try {
            val result = connection.createStatement().executeQuery("SELECT * FROM public.present WHERE id_present = $idPresent")
            if (result.next()) {
                val id = result.getInt(1)
                val text = result.getString(2)
                val idSender = result.getInt(3)
                val image = result.getString(4)
                val keyOpen = result.getString(5)
                val link = result.getString(6)

                return Present(id, text, idSender, image, keyOpen, link)
            }
            null
        } catch (_: Exception) {
            null
        }
    }

    override fun deleteItem(filter: Int): Boolean {
        TODO("Not yet implemented")
    }
}