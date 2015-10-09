package com.example.myact;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity {

	private List<Book> books = new ArrayList<Book>();
	private AutoCompleteTextView act;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		initView();
	}

	private void initView() {
		act = (AutoCompleteTextView) this.findViewById(R.id.myact);
		MyActAdapter adapter = new MyActAdapter(this,books, 2);
		act.setAdapter(adapter);
//		act.setThreshold(0);
	}

	private void initData() {
		Book b1 = new Book(1, "三国演义", "罗贯中", 38, "sanguoyanyi");
		Book b2 = new Book(2, "红楼梦", "曹雪芹", 25, "hongloumeng");
		Book b21 = new Book(2, "红楼梦1", "曹雪芹1", 25, "hongloumeng");
		Book b22 = new Book(2, "红楼梦2", "曹雪芹2", 25, "hongloumeng");
		Book b3 = new Book(3, "西游记", "吴承恩", 43, "xiyouji");
		Book b4 = new Book(4, "水浒传", "施耐庵", 72, "shuihuzhuan");
		Book b5 = new Book(5, "随园诗话", "袁枚", 32, "suiyuanshihua");
		Book b6 = new Book(6, "说文解字", "许慎", 14, "shuowenjiezi");
		Book b7 = new Book(7, "文心雕龙", "刘勰", 18, "wenxindiaolong");
		books.add(b1);
		books.add(b2);
		books.add(b21);
		books.add(b22);
		books.add(b3);
		books.add(b4);
		books.add(b5);
		books.add(b6);
		books.add(b7);
	}
}
