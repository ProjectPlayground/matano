package matano.apkode.net.matano.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import matano.apkode.net.matano.R;


public class MainFramelayoutCategorieHolder extends RecyclerView.ViewHolder {
    private Button btnMainFragment;

    public MainFramelayoutCategorieHolder(View itemView) {
        super(itemView);
    }

    public void bind(String s) {
        btnMainFragment = (Button) itemView.findViewById(R.id.btnMainFragment);

        if (null != btnMainFragment) {
            btnMainFragment.setText(s);
        }
    }

}
