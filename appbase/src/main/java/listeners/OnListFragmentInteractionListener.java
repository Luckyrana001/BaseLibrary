package listeners;

import androidluckyguys.baselibrary.BaseModel;

/**
 * Created by Sanka on 9/14/16.
 */
public interface OnListFragmentInteractionListener {
    void onListFragmentInteraction(BaseModel item);
    void onListLongPressFragmentInteraction(Object item);
    void onListItemDeleteClickInteraction(Object item);
}
