package com.news.example.myproject.ui.main.home.sort;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.library.util.VerificationUtils;
import com.library.widgets.dialog.BaseDialogFragment;
import com.news.example.myproject.R;
import com.news.example.myproject.model.sort.NewsSortInfo;
import com.news.example.myproject.model.sort.SortEditAdapter;
import com.news.example.myproject.model.sort.SortFilter;
import com.news.example.myproject.model.sort.SortInfoData;
import com.news.example.myproject.ui.main.home.HomeFragment;
import com.news.example.myproject.ui.main.recommend.RecommendFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 分类顺序编辑
 *
 * @author tanghao
 * @date 2018/8/28.
 */
@SuppressLint("ValidFragment")
public class SortEditFragment extends BaseDialogFragment {

    final String TAG = "SortFragment";

    private Context context;
    private SortEditFragment.Builder mBuilder;
    private RecyclerView rvMine, rvMore;
    private TextView tvEdit, tvTip;
    private SortEditAdapter sortAdapter, moreSortAdapter;
    private boolean isChange = false;

    public SortEditFragment() {

    }

    public SortEditFragment(SortEditFragment.Builder builder) {
        this.mBuilder = builder;
    }

    @Override
    protected void initWindows() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = mBuilder.dimAmount;
            windowParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            windowParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            windowParams.gravity = Gravity.BOTTOM;
            window.setAttributes(windowParams);
            window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_sort_view));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showDialog() {
        if (mBuilder == null || mBuilder.getFragmentManager() == null) {
            return;
        }
        FragmentTransaction ft = mBuilder.getFragmentManager().beginTransaction();
        ft.add(this, TAG);
        ft.commitAllowingStateLoss();
        //AppDanaClient.postShowEvent(mBuilder.getHomeFragment().getActivity(), this.getClass());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setWindowAnimations(R.style.dialog_fragment_anim);
        }
        setCancelable(false);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_sort, null);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        sortAdapter = new SortEditAdapter(mBuilder.getSortList());
        rvMine.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        itemTouchHelper.attachToRecyclerView(rvMine);
        rvMine.setAdapter(sortAdapter);
        rvMine.setNestedScrollingEnabled(false);
        sortAdapter.setOnLongPressListener((helper, item) -> {
            if (helper.getAdapterPosition() < mBuilder.getFixList().size()) {
                return;
            }
            tvTip.setText(R.string.drag_and_drop_to_sort);
            itemTouchHelper.startDrag(helper);
        });
        sortAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (VerificationUtils.isFastDoubleClick(R.id.rv_mine)) {
                return;
            }
            if (isEdit()) {
                if (position < mBuilder.getFixList().size()) {
                    return;
                }
                isChange = true;
                toMoreList(position);
            } else if (mBuilder != null && mBuilder.getEditSortListCallback() != null) {

                mBuilder.getEditSortListCallback().editSortList(isChange, position, SortFilter.INSTANCE.divideList(mBuilder.getAllList()));
                dismiss();
            }
        });

        //更多Adapter
        moreSortAdapter = new SortEditAdapter(mBuilder.getMoreSortList());
        rvMore.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvMore.setNestedScrollingEnabled(false);
        rvMore.setAdapter(moreSortAdapter);
        moreSortAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (VerificationUtils.isFastDoubleClick(R.id.rv_more)) {
                return;
            }
            isChange = true;
            toMineList(position);
        });
    }

    /**
     * 操作一波
     *
     * @param position 操作位置
     */
    private void toMoreList(int position) {
        NewsSortInfo sortInfo = mBuilder.getSortList().remove(position);
        sortAdapter.notifyItemRemoved(position);
        sortAdapter.notifyItemRangeChanged(position, mBuilder.getSortList().size());
        //***
        sortInfo.setItemType(NewsSortInfo.MORE);
        mBuilder.getMoreSortList().add(0, sortInfo);
        moreSortAdapter.notifyItemInserted(0);
    }

    /**
     * 添加一波
     *
     * @param position 添加位置
     */
    private void toMineList(int position) {
        NewsSortInfo sortInfo = mBuilder.getMoreSortList().remove(position);
        moreSortAdapter.notifyItemRemoved(position);
        moreSortAdapter.notifyItemRangeChanged(position, mBuilder.getMoreSortList().size());
        sortInfo.setItemType(NewsSortInfo.CHOOSE);
        mBuilder.getSortList().add(sortInfo);
        sortAdapter.notifyItemInserted(mBuilder.getSortList().size() - 1);
    }

    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (toPosition < mBuilder.getFixList().size()) {
                return true;
            }
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mBuilder.getSortList(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mBuilder.getSortList(), i, i - 1);
                }
            }
            sortAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (viewHolder instanceof BaseViewHolder) {
                ((BaseViewHolder) viewHolder).getView(R.id.ivSortClose).setVisibility(View.VISIBLE);
                tvEdit.setText(R.string.finish);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);

            if (!sortAdapter.isShowClose) {
                tvEdit.setText(R.string.finish);
                sortAdapter.isShowClose = true;
                sortAdapter.notifyDataSetChanged();
            }
            isChange = true;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }
    });

    private void toHomeCallBack() {
        if (mBuilder != null && mBuilder.getEditSortListCallback() != null) {
            int index = RecommendFragment.HOME_INDEX;
            if (mBuilder.getPosSortInfo() != null) {
                index = mBuilder.getSortList().indexOf(mBuilder.getPosSortInfo());
            }
            if (index < 0) {
                index = RecommendFragment.HOME_INDEX;
            }
            mBuilder.getEditSortListCallback().editSortList(isChange, index, SortFilter.INSTANCE.divideList(mBuilder.getAllList()));
        }
    }

    private void initView(View v) {
        v.findViewById(R.id.rl_close).setOnClickListener(v12 -> {
            if (VerificationUtils.isFastDoubleClick(R.id.rl_close)) {
                return;
            }
            toHomeCallBack();
            dismiss();
        });
        View mLayoutParent = v.findViewById(R.id.mLayoutParent);
        tvEdit = v.findViewById(R.id.tv_edit);
        tvTip = v.findViewById(R.id.tv_tip);
        rvMine = v.findViewById(R.id.rv_mine);
        rvMore = v.findViewById(R.id.rv_more);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLayoutParent.setPadding(0, getStatusBarHeight(), 0, 0);
        }

        tvEdit.setOnClickListener(v1 -> {
            String text = tvEdit.getText().toString();
            if (text.equals(getString(R.string.editor))) {
                tvEdit.setText(getString(R.string.finish));
                sortAdapter.isShowClose = true;
                tvTip.setText(R.string.drag_and_drop_to_sort);
            } else {
                tvEdit.setText(getString(R.string.editor));
                sortAdapter.isShowClose = false;
                tvTip.setText(R.string.click_go_to_sort);
            }
            sortAdapter.notifyDataSetChanged();
        });
    }

    /**
     * 是否编辑状态
     */
    private boolean isEdit() {
        String text = tvEdit.getText().toString();
        return ObjectUtils.equals(getString(R.string.finish), text);
    }

    public static class Builder implements Serializable {

        /**
         * 背景透明度
         */
        private float dimAmount = 0.6f;
        private FragmentManager fragmentManager;
        private HomeFragment homeFragment;
        private EditSortListCallback mEditSortListCallback;
        private NewsSortInfo posSortInfo;
        private SortInfoData sortRes;

        private List<NewsSortInfo> sortList;
        private List<NewsSortInfo> fixList;
        private List<NewsSortInfo> moreList;
        private List<NewsSortInfo> allList = new ArrayList<>();

        public SortEditFragment.Builder setSortData(
                HomeFragment homeFragment,
                EditSortListCallback editSortListCallback,
                NewsSortInfo posSortInfo,
                SortInfoData sortRes) {
            this.homeFragment = homeFragment;
            this.mEditSortListCallback = editSortListCallback;
            this.posSortInfo = posSortInfo;
            this.sortRes = sortRes;
            getSortList();
            getFixList();
            getMoreSortList();
            return this;
        }

        public HomeFragment getHomeFragment() {
            return homeFragment;
        }

        public NewsSortInfo getPosSortInfo() {
            return posSortInfo;
        }

        public SortEditFragment.Builder setDimAmount(Float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public SortEditFragment.Builder setFragmentManager(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            return this;
        }

        public FragmentManager getFragmentManager() {
            return fragmentManager;
        }

        public float getDimAmount() {
            return dimAmount;
        }

        public SortEditFragment create() {
            return new SortEditFragment(this);
        }

        public SortInfoData getSortRes() {
            if (sortRes == null) {
                sortRes = new SortInfoData();
            }
            return sortRes;
        }

        public List<NewsSortInfo> getSortList() {
            if (sortList == null) {
                sortList = new ArrayList<>();
                sortList.addAll(getSortRes().getFixedList());
                sortList.addAll(getSortRes().getChooseList());
            }
            return sortList;
        }

        public List<NewsSortInfo> getFixList() {
            if (fixList == null) {
                fixList = new ArrayList<>();
                fixList.addAll(getSortRes().getFixedList());
            }
            return fixList;
        }

        public List<NewsSortInfo> getMoreSortList() {
            if (moreList == null) {
                moreList = new ArrayList<>();
                moreList.addAll(getSortRes().getEditList());
            }
            return moreList;
        }

        public List<NewsSortInfo> getAllList() {
            allList.clear();
            if (sortList != null) {
                allList.addAll(sortList);
            }
            if (moreList != null) {
                allList.addAll(moreList);
            }
            return allList;
        }

        public EditSortListCallback getEditSortListCallback() {
            return mEditSortListCallback;
        }
    }

    public interface EditSortListCallback {

        /**
         * 分类编辑回调
         *
         * @param isChange  是否有编辑,改动
         * @param showIndex 显示Fragment下标
         * @param sortData  源数据
         */
        void editSortList(boolean isChange, int showIndex, SortInfoData sortData);
    }

    /**
     * 获取手机屏幕状态栏高度
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
