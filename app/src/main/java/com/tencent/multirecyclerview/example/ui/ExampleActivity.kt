package com.tencent.multirecyclerview.example.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tencent.multirecyclerview.R
import com.tencent.multirecyclerview.example.bean.ItemBean
import com.tencent.multirecyclerview.example.itemtype.AItemType
import com.tencent.multirecyclerview.example.itemtype.BItemType
import com.tencent.multirecyclerview.example.itemtype.CItemType
import com.tencent.lib.widget.ItemType
import kotlinx.android.synthetic.main.activity_example.*
import java.util.ArrayList

class ExampleActivity : AppCompatActivity() {
    companion object{
        val DEMO_LINEAR_VERTICAL=0
        val DEMO_LINEAR_HORIEN=1
        val DEMO_GRID=2
        val DEMO_FALLS=3
        fun start(from:Context,demo:Int){
            val intent=Intent(from, ExampleActivity::class.java)
            intent.putExtra("demo",demo)
            from.startActivity(intent)
        }
    }

    val datas= ArrayList<ItemBean>()
    val types= ArrayList<ItemType<*>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        initData()

       when(intent.getIntExtra("demo", DEMO_LINEAR_VERTICAL)){
           DEMO_LINEAR_VERTICAL ->{linearVertical()}
           DEMO_LINEAR_HORIEN ->{linearHorizontal()}
           DEMO_GRID -> grid()
           DEMO_FALLS ->staggeredGrid()
       }
    }



    fun initData(){
        //添加ItemType
        types.add(AItemType())
        types.add(BItemType())
        types.add(CItemType())

        //模拟数据·
        datas.add(ItemBean(ItemBean.TYPE_A,"AAAAAAAA"))
        datas.add(ItemBean(ItemBean.TYPE_B,"BBBBB"))
        datas.add(ItemBean(ItemBean.TYPE_C,"CCCCC"))
        datas.add(ItemBean(ItemBean.TYPE_A,"AAAAAAAA"))
        datas.add(ItemBean(ItemBean.TYPE_A,"AAAAAAAA"))
        datas.add(ItemBean(ItemBean.TYPE_A,"AAAAAAAA"))
        datas.add(ItemBean(ItemBean.TYPE_C,"CCCCCCC"))
        datas.add(ItemBean(ItemBean.TYPE_A,"AAAAAAAA"))
        datas.add(ItemBean(ItemBean.TYPE_A,"AAAAAAAA"))
        datas.add(ItemBean(ItemBean.TYPE_C,"CCCCCC"))
        datas.add(ItemBean(ItemBean.TYPE_A,"AAAAAAAA"))
        datas.add(ItemBean(ItemBean.TYPE_A,"AAAAAAAA"))
        datas.add(ItemBean(ItemBean.TYPE_B,"BBBB"))
        datas.add(ItemBean(ItemBean.TYPE_A,"AAAAAAAA"))
        datas.add(ItemBean(ItemBean.TYPE_C,"CCCCCC"))
        datas.add(ItemBean(ItemBean.TYPE_A,"AAAAAAAA"))
        datas.add(ItemBean(ItemBean.TYPE_A,"AAAAAAAA"))



    }

    /***
     * 线性垂直
     */
    fun linearVertical(){
//        pagedRecyclerView
//                .linearBuilder()//获取实现线性效果的Builder
//                .buildLayoutManager(this)
////                .setItemTypes(types)//设置类型
//                .setDatas(datas)//设置数据
//                .setItemSpace(10,10,10,10)//Item间距
//                .build()
    }

    /**
     * 线性水平
     */
    fun linearHorizontal(){
//        pagedRecyclerView
//                .linearBuilder()//获取实现线性效果的Builder
//                .buildLayoutManager(this,RecyclerView.HORIZONTAL)
//                .setItemTypes(types)//设置类型
//                .setDatas(datas)//设置数据
//                .setItemSpace(10,10,10,10)//Item间距
//                .build()
    }

    /**
     * 网格
     */
    fun grid(){
        pagedRecyclerView
//                .gridBuilder(3)//获取实现网格效果的Builder
//                .buildLayoutManager(this)
//                .setItemTypes(types)//设置类型
//                .setDatas(datas)//设置数据
//                .setItemSpace(20,true)//Item间距
//                .build()
    }

    /**
     * 瀑布
     */
    fun staggeredGrid(){
//        pagedRecyclerView
//                .staggeredGridBuilder(StaggeredGridLayoutManager.GAP_HANDLING_NONE)//获取实现瀑布流效果的Builder
//                .buildLayoutManager(2,RecyclerView.VERTICAL)
//                .setItemTypes(types)//设置类型
//                .setDatas(datas)//设置数据
//                .setItemSpace(5,5,5,5)//Item间距
//                .build()
    }


}
