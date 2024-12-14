package com.github.theholychicken.utils

import com.github.theholychicken.GoodMod.Companion.mc
import kotlinx.coroutines.*
import net.minecraft.inventory.Container
import net.minecraft.inventory.ContainerChest
import net.minecraft.util.ChatComponentText

private val FORMATTING_CODE_PATTERN = Regex("§[0-9a-fk-or]", RegexOption.IGNORE_CASE)

/**
 * Returns the string without any minecraft formatting codes.
 */
val String?.noControlCodes: String
    get() = this?.let { FORMATTING_CODE_PATTERN.replace(it, "") } ?: ""

fun modMessage(message: Any?, prefix: Boolean = true) {
    mc.thePlayer?.addChatMessage(ChatComponentText(if (prefix) "§r§3good mod §r§f» §r$message§r" else message.toString()))
}

val ContainerChest.name: String
    get() = this.lowerChestInventory.displayName.unformattedText

suspend fun waitUntilLastItem(container: ContainerChest) = coroutineScope {
    val deferredResult = CompletableDeferred<Unit>()
    val startTime = System.currentTimeMillis()

    fun check() {
        if (System.currentTimeMillis() - startTime > 1000) {
            deferredResult.completeExceptionally(Exception("Promise rejected"))
            return
        } else if (container.inventory[container.inventory.size - 37] != null)
            deferredResult.complete(Unit)
        else {
            launch {
                delay(10)
                check()
            }
        }
    }

    launch { check() }

    deferredResult
}