package me.dhlee.mask

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import me.dhlee.properties.HttpLoggingProperties

class JsonBodyMasker(
    props: HttpLoggingProperties,
) : BodyMasker {
    private val mapper: ObjectMapper = ObjectMapper().apply { enable(SerializationFeature.INDENT_OUTPUT) }
    private val keysToMask: Set<String> = props.maskBody.map { it.lowercase() }.toSet()

    // TODO: maxBodyLength를 적용했을 때, body가 잘려서 json이 아닐 경우에 대한 처리
    //       이 경우, regEx로 body를 mask하는 방법도 고려해볼 수 있음 (우선, truncated라고만 표시)
    // TODO: LEAVES 마스킹 전략에 대한 기능 추가
    override fun mask(body: String?): String {
        return when {
            body.isNullOrBlank() -> ""
            isJson(body) -> maskJsonBody(body)
            else -> body
        }
    }

    private fun isJson(body: String): Boolean {
        return try {
            mapper.readTree(body)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun maskJsonBody(body: String): String {
        val node = mapper.readTree(body)
        val maskedNode = maskNode(node)
        return mapper.writeValueAsString(maskedNode)
    }

    private fun maskNode(node: JsonNode): JsonNode {
        return when {
            node.isObject -> {
                val obj = node.deepCopy<ObjectNode>()
                for ((key, value) in obj.fields()) {
                    if (key.lowercase() in keysToMask && value.isTextual) {
                        obj.put(key, "****")
                    } else {
                        obj.set(key, maskNode(value))
                    }
                }
                obj
            }

            node.isArray -> {
                val arr = node.deepCopy<ArrayNode>()
                for (i in 0 until arr.size()) {
                    arr.set(i, maskNode(arr[i]))
                }
                arr
            }

            else -> node
        }
    }
}