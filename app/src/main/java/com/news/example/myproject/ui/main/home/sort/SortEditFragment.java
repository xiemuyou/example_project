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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.library.util.VerificationUtils;
import com.library.widgets.dialog.BaseDialogFragment;
import com.library.widgets.statusbar.StatusBarTools;
import com.news.example.myproject.R;
import com.news.example.myproject.model.sort.NewsSortInfo;
import com.news.example.myproject.model.sort.SortFilter;
import com.news.example.myproject.model.sort.SortInfoData;
import com.news.example.myproject.ui.main.recommend.RecommendFragment;
import com.news.example.myproject.ui.test.OnRecyclerItemClickListener;
import com.news.example.myproject.ui.test.SortEditAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
    private RecyclerView rvSortEdit;
    private TextView tvSortEdit, tvTip;
    private DelegateAdapter delegateAdapter;
    private boolean isChange = false;
    private List<NewsSortInfo> mDataList;

    public SortEditFragment() {

    }

    private SortEditFragment(SortEditFragment.Builder builder) {
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
        initRecyclerView();
        initData();
        return view;
    }

    private void initRecyclerView() {
        mDataList = new ArrayList<>();

        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(context);
        rvSortEdit.setLayoutManager(layoutManager);

        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        rvSortEdit.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);
        layoutManager.setRecycleOffset(3000);

        delegateAdapter = new DelegateAdapter(layoutManager, true);
        rvSortEdit.setAdapter(delegateAdapter);

        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        GridLayoutHelper helper = new GridLayoutHelper(4);
        helper.setMargin(SizeUtils.dp2px(12F), 0, 0, 0);
        helper.setAutoExpand(false);

        mDataList.addAll(mBuilder.getSortList());
        SortEditAdapter sortAdapter = new SortEditAdapter(context, mBuilder.getSortList(), helper);
        adapters.add(sortAdapter);

        List<NewsSortInfo> titleList = new ArrayList<>(1);
        NewsSortInfo titleSort = new NewsSortInfo();
        titleSort.setItemType(NewsSortInfo.TITLE);
        titleList.add(titleSort);

        mDataList.add(titleSort);
        SortEditAdapter titleAdapter = new SortEditAdapter(context, titleList, new LinearLayoutHelper());
        adapters.add(titleAdapter);

        GridLayoutHelper helper1 = new GridLayoutHelper(4);
        helper1.setAutoExpand(false);
        helper1.setMargin(SizeUtils.dp2px(12F), 0, 0, 0);

        mDataList.addAll(mBuilder.getMoreSortList());
        SortEditAdapter moreSortAdapter = new SortEditAdapter(context, mBuilder.getMoreSortList(), helper1);
        adapters.add(moreSortAdapter);
        delegateAdapter.setAdapters(adapters);
    }

    private void initData() {
        itemTouchHelper.attachToRecyclerView(rvSortEdit);
        rvSortEdit.setNestedScrollingEnabled(false);
        rvSortEdit.addOnItemTouchListener(new OnRecyclerItemClickListener(rvSortEdit) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder != null) {
                    setItemClick(viewHolder);
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder) {
                NewsSortInfo info = null;
                int position = viewHolder != null ? viewHolder.getAdapterPosition() : -1;
                if (mDataList != null && mDataList.size() > position) {
                    info = mDataList.get(position);
                }
                int itemType = info != null && info.getItemType() != null ? info.getItemType() : -1;
                if (itemType == NewsSortInfo.CHOOSE) {
                    tvTip.setText(R.string.drag_and_drop_to_sort);
                    itemTouchHelper.startDrag(viewHolder);
                }
            }
        });
    }

    private void setItemClick(@NonNull RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        if (VerificationUtils.isFastDoubleClick(R.id.rvSortEdit)) {
            return;
        }
        NewsSortInfo info = null;
        if (mDataList != null && mDataList.size() > position) {
            info = mDataList.get(position);
        }
        int itemType = info != null && info.getItemType() != null ? info.getItemType() : -1;
        switch (itemType) {
            case NewsSortInfo.CHOOSE:
            case NewsSortInfo.FIXED:
                if (VerificationUtils.isFastDoubleClick(R.id.rvSortEdit)) {
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
                break;

            case NewsSortInfo.MORE:
                isChange = true;
                toMineList(position);
                break;

            default:
                break;
        }
    }

    /**
     * 操作一波
     *
     * @param position 操作位置
     */
    private void toMoreList(int position) {
//        NewsSortInfo sortInfo = mBuilder.getSortList().remove(position);
//        sortAdapter.notifyItemRemoved(position);
//        sortAdapter.notifyItemRangeChanged(position, mBuilder.getSortList().size());
//        sortInfo.setItemType(NewsSortInfo.MORE);
//        mBuilder.getMoreSortList().add(0, sortInfo);
//        moreSortAdapter.notifyItemInserted(0);
    }

    /**
     * 添加一波
     *
     * @param position 添加位置
     */
    private void toMineList(int position) {
//        NewsSortInfo sortInfo = mBuilder.getMoreSortList().remove(position);
//        moreSortAdapter.notifyItemRemoved(position);
//        moreSortAdapter.notifyItemRangeChanged(position, mBuilder.getMoreSortList().size());
//        sortInfo.setItemType(NewsSortInfo.CHOOSE);
//        mBuilder.getSortList().add(sortInfo);
//        sortAdapter.notifyItemInserted(mBuilder.getSortList().size() - 1);
    }

    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            NewsSortInfo formInfo = getNewsSortInfoByPosition(fromPosition);
            int itemType = (formInfo != null && formInfo.getItemType() != null) ? formInfo.getItemType() : -1;
            if (itemType != NewsSortInfo.CHOOSE) {
                return false;
            }
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            NewsSortInfo toInfo = getNewsSortInfoByPosition(fromPosition);
            int toItemType = (toInfo != null && toInfo.getItemType() != null) ? toInfo.getItemType() : -1;
            if (toItemType != NewsSortInfo.TITLE) {
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mDataList, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mDataList, i, i - 1);
                    }
                }
                delegateAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (viewHolder instanceof BaseViewHolder) {
                ((BaseViewHolder) viewHolder).getView(R.id.ivSortClose).setVisibility(View.VISIBLE);
                tvSortEdit.setText(R.string.finish);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
//            if (!sortAdapter.isShowClose()) {
//                tvSortEdit.setText(R.string.finish);
//                sortAdapter.setIsShowClose(true);
//                sortAdapter.notifyDataSetChanged();
//            }
            isChange = true;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }
    });

    private NewsSortInfo getNewsSortInfoByPosition(int position) {
        if (mDataList == null || mDataList.size() < position) {
            return null;
        }
        return mDataList.get(position);
    }

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
        tvSortEdit = v.findViewById(R.id.tvSortEdit);
        tvTip = v.findViewById(R.id.tvSortTip);
        rvSortEdit = v.findViewById(R.id.rvSortEdit);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLayoutParent.setPadding(0, StatusBarTools.getStatusBarHeight(this), 0, 0);
        }

        tvSortEdit.setOnClickListener(v1 -> {
//            String text = tvSortEdit.getText().toString();
//            if (text.equals(getString(R.string.editor))) {
//                tvSortEdit.setText(getString(R.string.finish));
//                sortAdapter.setIsShowClose(true);
//                tvTip.setText(R.string.drag_and_drop_to_sort);
//            } else {
//                tvSortEdit.setText(getString(R.string.editor));
//                sortAdapter.setIsShowClose(false);
//                tvTip.setText(R.string.click_go_to_sort);
//            }
//            sortAdapter.notifyDataSetChanged();
        });
    }

    /**
     * 是否编辑状态
     */
    private boolean isEdit() {
        String text = tvSortEdit.getText().toString();
        return ObjectUtils.equals(getString(R.string.finish), text);
    }

    public static class Builder implements Serializable {

        /**
         * 背景透明度
         */
        private float dimAmount = 0.6f;
        private FragmentManager fragmentManager;
        private EditSortListCallback mEditSortListCallback;
        private NewsSortInfo posSortInfo;
        private SortInfoData sortRes;

        private List<NewsSortInfo> sortList;
        private List<NewsSortInfo> fixList;
        private List<NewsSortInfo> moreList;
        private List<NewsSortInfo> allList = new ArrayList<>();

        public SortEditFragment.Builder setSortData(
                EditSortListCallback editSortListCallback,
                NewsSortInfo posSortInfo,
                SortInfoData sortRes) {
            this.mEditSortListCallback = editSortListCallback;
            this.posSortInfo = posSortInfo;
            this.sortRes = sortRes;
            getSortList();
            getFixList();
            getMoreSortList();
            return this;
        }

        NewsSortInfo getPosSortInfo() {
            return posSortInfo;
        }

        public SortEditFragment.Builder setFragmentManager(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            return this;
        }

        FragmentManager getFragmentManager() {
            return fragmentManager;
        }

        public SortEditFragment create() {
            return new SortEditFragment(this);
        }

        SortInfoData getSortRes() {
            if (sortRes == null) {
                sortRes = new SortInfoData();
            }
            return sortRes;
        }

        List<NewsSortInfo> getSortList() {
            if (sortList == null) {
                sortList = new ArrayList<>();
                sortList.addAll(getSortRes().getFixedList());
                sortList.addAll(getSortRes().getChooseList());
            }
            return sortList;
        }

        List<NewsSortInfo> getFixList() {
            if (fixList == null) {
                fixList = new ArrayList<>();
                fixList.addAll(getSortRes().getFixedList());
            }
            return fixList;
        }

        List<NewsSortInfo> getMoreSortList() {
            if (moreList == null) {
                moreList = new ArrayList<>();
                moreList.addAll(getSortRes().getEditList());
            }
            return moreList;
        }

        List<NewsSortInfo> getAllList() {
            allList.clear();
            if (sortList != null) {
                allList.addAll(sortList);
            }
            if (moreList != null) {
                allList.addAll(moreList);
            }
            return allList;
        }

        EditSortListCallback getEditSortListCallback() {
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
