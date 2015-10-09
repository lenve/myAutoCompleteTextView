package com.example.myact;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class MyActAdapter extends BaseAdapter implements Filterable {

	private List<Book> books;
	private Context context;
	private List<Book> mFilterBooks;
	private ArrayFilter mArrayFilter;
	private List<Set<String>> pinYinList;
	private List<Set<String>> pinYinAllList;
	private int maxMatch;

	public MyActAdapter(Context context, List<Book> books, int maxMatch) {
		this.books = books;
		this.context = context;
		this.maxMatch = maxMatch;
		initPinYinList();
	}

	private void initPinYinList() {
		pinYinList = new ArrayList<Set<String>>();
		pinYinAllList = new ArrayList<Set<String>>();
		PinYin4j pinyin = new PinYin4j();
		for (int i = 0; i < books.size(); i++) {
			pinYinList.add(pinyin.getPinyin(books.get(i).getAuthor().toString()));
			pinYinAllList.add(pinyin.getAllPinyin(books.get(i).getAuthor().toString()));
		}
	}

	@Override
	public int getCount() {
		return books.size();
	}

	@Override
	public Object getItem(int position) {
		return books.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			// convertView = LayoutInflater.from(context).inflate(
			// R.layout.act_item, null);
			convertView = View.inflate(context, R.layout.act_item, null);
			viewHolder.id = (TextView) convertView.findViewById(R.id.id_book);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.name_book);
			viewHolder.author = (TextView) convertView
					.findViewById(R.id.author_book);
			viewHolder.price = (TextView) convertView
					.findViewById(R.id.price_book);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Book book = books.get(position);
		viewHolder.id.setText(book.getId() + "");
		viewHolder.name.setText(book.getName());
		viewHolder.author.setText(book.getAuthor());
		viewHolder.price.setText(book.getPrice() + "");
		return convertView;
	}

	class ViewHolder {
		TextView id, name, author, price;
	}

	@Override
	public Filter getFilter() {
		if (mArrayFilter == null) {
			mArrayFilter = new ArrayFilter();
		}
		return mArrayFilter;
	}

	private class ArrayFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (mFilterBooks == null) {
				mFilterBooks = new ArrayList<Book>(books);
			}
			// 如果没有过滤条件则不过滤
			if (constraint == null || constraint.length() == 0) {
				results.values = mFilterBooks;
				results.count = mFilterBooks.size();
			} else {
				List<Book> retList = new ArrayList<Book>();
				// 过滤条件
				String str = constraint.toString().toLowerCase();
				Book book;
				// 循环变量数据源，如果有属性满足过滤条件，则添加到result中
				for (int i = 0;i<mFilterBooks.size();i++) {
					book = mFilterBooks.get(i);
					if (book.getAuthor().contains(str)
							|| book.getName().contains(str)
							|| (book.getId() + "").contains(str)
							|| (book.getPrice() + "").contains(str)
							|| book.getPinyin().contains(str)) {
						retList.add(book);
					}
					else{
						Set<String> pinyinSet = pinYinList.get(i);
						Iterator<String> pinyin = pinyinSet.iterator();
						while(pinyin.hasNext()){
							if(pinyin.next().toString().contains(str)){
								retList.add(book);
								break;
							}
						}
						Set<String> pinyinAllSet = pinYinAllList.get(i);
						Iterator<String> pinyinAll = pinyinAllSet.iterator();
						while(pinyinAll.hasNext()){
							if(pinyinAll.next().toString().contains(str)){
								retList.add(book);
								break;
							}
						}
					}
//					if (maxMatch > 0) {
//						if (retList.size() > maxMatch - 1) {
//							break;
//						}
//					}
				}
				results.values = retList;
				results.count = retList.size();
			}
			return results;
		}

		// 在这里返回过滤结果
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// notifyDataSetInvalidated()，会重绘控件（还原到初始状态）
			// notifyDataSetChanged()，重绘当前可见区域
			books = (List<Book>) results.values;
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}

	}

}
