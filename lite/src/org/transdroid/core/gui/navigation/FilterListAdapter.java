package org.transdroid.core.gui.navigation;

import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.transdroid.core.R;
import org.transdroid.core.gui.lists.SimpleListItem;
import org.transdroid.core.gui.lists.SimpleListItemAdapter;
import org.transdroid.core.gui.navigation.NavigationSelectionView.NavigationFilterManager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import com.commonsware.cwac.merge.MergeAdapter;

/**
 * List adapter that holds filter items, that is, servers, view types and labels. A header item is inserted where
 * appropriate.
 * @author Eric Kok
 */
@EBean
public class FilterListAdapter extends MergeAdapter implements SpinnerAdapter {

	@RootContext
	protected Context context;
	private SimpleListItemAdapter serverItems = null;
	private SimpleListItemAdapter statusTypeItems = null;
	private SimpleListItemAdapter labelItems = null;
	private NavigationFilterManager navigationFilterManager;

	/**
	 * Stores which screen, or manager, handles navigation selection and display
	 * @param manager The navigation manager, which knows about the currently selected filter and server
	 * @return Itself, for method chaining
	 */
	public FilterListAdapter setNavigationFilterManager(NavigationFilterManager manager) {
		this.navigationFilterManager = manager;
		return this;
	}
	
	/**
	 * Update the list of available servers
	 * @param servers The new list of available servers
	 */
	public void updateServers(List<? extends SimpleListItem> servers) {
		if (this.serverItems == null && servers != null) {
			addView(FilterSeparatorView_.build(context).setText(context.getString(R.string.navigation_servers)), false);
			this.serverItems = new SimpleListItemAdapter(context, servers);
			addAdapter(serverItems);
		} else if (this.serverItems != null && servers != null) {
			this.serverItems.update(servers);
		} else {
			this.serverItems = null;
		}
	}

	/**
	 * Update the list of available status types
	 * @param statusTypes The new list of available status types
	 */
	public void updateStatusTypes(List<? extends SimpleListItem> statusTypes) {
		if (this.statusTypeItems == null && statusTypes != null) {
			addView(FilterSeparatorView_.build(context).setText(context.getString(R.string.navigation_status)), false);
			this.statusTypeItems = new SimpleListItemAdapter(context, statusTypes);
			addAdapter(statusTypeItems);
		} else if (this.statusTypeItems != null && statusTypes != null) {
			this.statusTypeItems.update(statusTypes);
		} else {
			this.statusTypeItems = null;
		}
	}

	/**
	 * Update the list of available labels
	 * @param labels The new list of available labels
	 */
	public void updateLabels(List<? extends SimpleListItem> labels) {
		if (this.labelItems == null && labels != null) {
			addView(FilterSeparatorView_.build(context).setText(context.getString(R.string.navigation_labels)), false);
			this.labelItems = new SimpleListItemAdapter(context, labels);
			addAdapter(labelItems);
		} else if (this.serverItems != null && labels != null) {
			this.labelItems.update(labels);
		} else {
			this.labelItems = null;
		}
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		NavigationSelectionView filterItemView;
		if (convertView == null || !(convertView instanceof NavigationSelectionView)) {
			filterItemView = NavigationSelectionView_.build(context).setNavigationFilterManager(navigationFilterManager);
		} else {
			filterItemView = (NavigationSelectionView) convertView;
		}
		filterItemView.bind();
		return filterItemView;
	}
	
}
