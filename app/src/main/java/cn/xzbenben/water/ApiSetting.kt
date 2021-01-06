package cn.xzbenben.water

import android.content.Context
import android.widget.Toast
import okhttp3.logging.HttpLoggingInterceptor
import org.pulp.fastapi.Setting
import org.pulp.fastapi.i.*
import org.pulp.fastapi.model.Error

class ApiSetting(var ctx: Context) : Setting {
    override fun onGetPathConverter(): PathConverter? {
        return null
    }

    override fun onAfterParse(): InterpreterParserAfter<*>? {
        return null
    }

    override fun <T : Any?> onCustomParse(dataClass: Class<T>?): InterpreterParserCustom<T>? {
        return null
    }

    override fun onGetPageCondition(): PageCondition<*>? {
        return null
    }

    override fun onGetCacheSize(): Long {
        return 10 * 1024
    }

    override fun onToastError(error: Error) {
        Toast.makeText(ctx, error.msg, Toast.LENGTH_SHORT).show()
    }

    override fun onGetCommonParams(): MutableMap<String, String>? {
        return null
    }

    override fun onBeforeParse(): InterpreterParseBefore? {
        return null
    }

    override fun onGetBaseUrl(): String {
        return "http://baidu.com"
    }

    override fun onGetConnectTimeout(): Int {
        return 3000
    }


    override fun onCustomLoggerLevel(): HttpLoggingInterceptor.Level? {
        return null
    }

    override fun onGetCacheDir(): String {
        return "fastapi"
    }

    override fun onGetCommonHeaders(): MutableMap<String, String>? {
        return null
    }

    override fun onGetReadTimeout(): Int {
        return 3000
    }

    override fun onErrorParse(): InterpreterParseError? {
        return null
    }

    override fun onGetApplicationContext(): Context {
        return ctx
    }

    override fun onCustomLogger(): HttpLoggingInterceptor.Logger? {
        return null
    }
}