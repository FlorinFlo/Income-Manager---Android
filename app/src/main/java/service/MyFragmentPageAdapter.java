package service;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import fragment.Bills_Fragment;
import fragment.Budget_Fragment;
import fragment.Expenses_Fragment;
import fragment.Expenses_List_Fragment;
import fragment.OtherIncome_Fragment;
import fragment.Salary_Fragment;
import fragment.Tools_Fragment;
import incomemanager.Expenses_Activity;
import incomemanager.Income_Activity;
import incomemanager.MainActivity;

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

	private int PAGE_COUNT;
	private String tabTitles[];
	private Context context;

	public MyFragmentPageAdapter(FragmentManager fm, String tabTitles[],
			Context context) {

		super(fm);

		this.tabTitles = tabTitles;
		Log.w("TABTITLE",tabTitles.length+"");
		this.context = context;
		 PAGE_COUNT =this.tabTitles.length;

	}

	@Override
	public Fragment getItem(int arg0) {


		Activity activity = (Activity) context;
		if (activity instanceof Income_Activity) {
			switch (arg0) {

			case 0:
				OtherIncome_Fragment fragment1 = new OtherIncome_Fragment();
//				Bundle arguments1=new Bundle();
//				arguments1.putString("pageIndex", Integer.toString(arg0 + 1));
//				fragment1.setArguments(arguments1);
				return fragment1;
			case 1:
				Salary_Fragment fragment2 = new Salary_Fragment();

				return fragment2;
			}
		} else if(activity instanceof Expenses_Activity){
			switch (arg0) {

			case 0:
				Expenses_Fragment fragment1 = new Expenses_Fragment();
				return fragment1;
			case 1:
				Bills_Fragment fragment2 = new Bills_Fragment();
				return fragment2;
			}
		}else if(activity instanceof MainActivity){

			switch (arg0){
				case 0:
					Budget_Fragment budgetFragment=new Budget_Fragment();

					return budgetFragment;

				case 1:
					Expenses_List_Fragment expenses_list_fragment=new Expenses_List_Fragment();

					return expenses_list_fragment;

				case 2:
					Tools_Fragment tools_fragment=new Tools_Fragment();

					return  tools_fragment;

			}
		}


		return null;
	}

	@Override
	public int getCount() {

		return PAGE_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return tabTitles[position];
	}

}
