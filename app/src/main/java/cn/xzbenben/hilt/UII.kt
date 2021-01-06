package cn.xzbenben.hilt

interface UII


interface TestUII : UII{
    fun onData()
}
interface TestUII2 : UII{
    fun onData(string: String)
}