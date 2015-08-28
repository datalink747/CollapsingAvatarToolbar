package com.sloydev.collapsingavatartoolbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CollapsingAvatarToolbar extends LinearLayout implements AppBarLayout.OnOffsetChangedListener {

    private static final float DEFAULT_MAX_PADDING = 72 * 3;
    private static final float DEFAULT_MIN_PADDING = 16 * 3;

    private static final float DEFAULT_MAN_IMAGE_SIZE = 60 * 3;
    private static final float DEFAULT_MIN_IMAGE_SIZE = 40 * 3;

    private static final float DEFAULT_MAX_TEXT_SIZE = 19;
    private static final float DEFAULT_MIN_TEXT_SIZE = 18;

    private View avatarView;
    private TextView titleView;

    private float collapsedPadding;
    private float expandedPadding;

    private float expandedImageSize;
    private float collapsedImageSize;

    private float collapsedTextSize;
    private float expandedTextSize;

    private boolean valuesCalculatedAlready = false;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private float collapsedHeight;
    private float expandedHeight;
    private float maxOffset;

    public CollapsingAvatarToolbar(Context context) {
        super(context);
        init();
    }

    public CollapsingAvatarToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CollapsingAvatarToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CollapsingAvatarToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }

    @NonNull
    private AppBarLayout findParentAppBarLayout() {
        ViewParent parent = this.getParent();
        if (parent instanceof AppBarLayout) {
            return ((AppBarLayout) parent);
        } else if (parent.getParent() instanceof AppBarLayout) {
            return ((AppBarLayout) parent.getParent());
        } else {
            throw new IllegalStateException("Must be inside an AppBarLayout"); //TODO actually, a collapsingtoolbar
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        findViews();
        if (!isInEditMode()) {
            appBarLayout.addOnOffsetChangedListener(this);
        } else {
            setExpandedValuesForEditMode();
        }
    }

    private void setExpandedValuesForEditMode() {
        calculateValues();
        updateViews(1f, 0);
    }

    private void findViews() {
        appBarLayout = findParentAppBarLayout();
        toolbar = findSiblingToolbar();
        avatarView = findAvatar();
        titleView = findTitle();
    }

    @NonNull
    private View findAvatar() {
        View avatar = this.findViewById(R.id.cat_avatar);
        if (avatar == null) {
            throw new IllegalStateException("View with id ta_avatar not found");
        }
        return avatar;
    }

    @NonNull
    private TextView findTitle() {
        TextView title = (TextView) this.findViewById(R.id.cat_title);
        if (title == null) {
            throw new IllegalStateException("TextView with id ta_title not found");
        }
        return title;
    }

    @NonNull
    private Toolbar findSiblingToolbar() {
        ViewGroup parent = ((ViewGroup) this.getParent());
        for (int i = 0, c = parent.getChildCount(); i < c; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof Toolbar) {
                return (Toolbar) child;
            }
        }
        throw new IllegalStateException("No toolbar found as sibling");
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if (!valuesCalculatedAlready) {
            calculateValues();
            valuesCalculatedAlready = true;
        }
        float expandedPercentage = 1 - (-offset / maxOffset);
        updateViews(expandedPercentage, offset);
    }

    private void calculateValues() {
        collapsedHeight = toolbar.getHeight();
        expandedHeight = appBarLayout.getHeight() - toolbar.getHeight();
        maxOffset = expandedHeight;
    }

    private void updateViews(float expandedPercentage, int currentOffset) {
        float inversePercentage = 1 - expandedPercentage;
        float translation = -currentOffset + ((float) toolbar.getHeight() * expandedPercentage);

        float currHeight = collapsedHeight + (expandedHeight - collapsedHeight) * expandedPercentage;
        float currentPadding = expandedPadding + (collapsedPadding - expandedPadding) * inversePercentage;
        float currentImageSize = collapsedImageSize + (expandedImageSize - collapsedImageSize) * expandedPercentage;
        float currentTextSize = collapsedTextSize + (expandedTextSize - collapsedTextSize) * expandedPercentage;

        setContainerOffset(translation);
        setContainerHeight((int) currHeight);
        setPadding((int) currentPadding);
        setAvatarSize((int) currentImageSize);
        setTextSize(currentTextSize);
    }

    private void setContainerOffset(float translation) {
        this.setTranslationY(translation);
    }

    private void setContainerHeight(int currHeight) {
        this.getLayoutParams().height = currHeight;
    }

    private void setPadding(int currentPadding) {
        this.setPadding(currentPadding, 0, 0, 0);
    }

    private void setTextSize(float currentTextSize) {
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentTextSize);
    }

    private void setAvatarSize(int currentImageSize) {
        avatarView.getLayoutParams().height = currentImageSize;
        avatarView.getLayoutParams().width = currentImageSize;
    }
}
