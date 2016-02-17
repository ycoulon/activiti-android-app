/*
 *  Copyright (C) 2005-2015 Alfresco Software Limited.
 *
 * This file is part of Alfresco Activiti Mobile for Android.
 *
 * Alfresco Activiti Mobile for Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco Activiti Mobile for Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.activiti.android.app.fragments.settings;

import java.util.List;
import java.util.Map;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.activiti.android.app.R;
import com.activiti.android.app.activity.MainActivity;
import com.activiti.android.platform.account.ActivitiAccount;
import com.activiti.android.platform.account.ActivitiAccountManager;
import com.activiti.android.platform.intent.IntentUtils;
import com.activiti.android.ui.fragments.AlfrescoFragment;
import com.activiti.android.ui.fragments.builder.LeafFragmentBuilder;
import com.activiti.android.ui.holder.HolderUtils;
import com.activiti.android.ui.holder.TwoLinesViewHolder;
import com.activiti.android.ui.utils.UIUtils;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

public class GeneralSettingsFragment extends AlfrescoFragment
{
    public static final String TAG = GeneralSettingsFragment.class.getName();

    // ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    // ///////////////////////////////////////////////////////////////////////////
    public GeneralSettingsFragment()
    {
        setHasOptionsMenu(true);
    }

    protected static GeneralSettingsFragment newInstanceByTemplate(Bundle b)
    {
        GeneralSettingsFragment cbf = new GeneralSettingsFragment();
        cbf.setArguments(b);
        return cbf;
    }

    // ///////////////////////////////////////////////////////////////////////////
    // LIFE CYCLE
    // ///////////////////////////////////////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setRootView(inflater.inflate(R.layout.fr_settings, container, false));

        // TITLE
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.settings);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);

        // ADD Accounts
        List<ActivitiAccount> accounts = ActivitiAccountManager.retrieveAccounts(getActivity());
        View accountView;
        LinearLayout accountContainer = (LinearLayout) viewById(R.id.settings_accounts_container);
        TwoLinesViewHolder vh;
        for (ActivitiAccount account : accounts)
        {
            accountView = inflater.inflate(R.layout.row_two_lines_borderless, accountContainer, false);
            accountView.setTag(account.getId());
            HolderUtils.configure(accountView, account.getUsername(), account.getLabel(),
                    R.drawable.ic_account_circle_grey);
            accountView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AccountSettingsFragment.with(getActivity()).accountId((Long) v.getTag()).display();
                }
            });
            accountContainer.addView(accountView);
        }

        // PLay Store
        vh = HolderUtils.configure(viewById(R.id.settings_google_play), getString(R.string.settings_google_play_title),
                getString(R.string.settings_google_play_summary), -1);
        viewById(R.id.settings_google_play).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startPlayStore();
            }
        });

        // Version Number
        String versionNumber;
        try
        {
            StringBuilder sb = new StringBuilder()
                    .append(getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName)
                    .append(".").append(getText(R.string.bamboo_buildnumber));
            versionNumber = sb.toString();
        }
        catch (PackageManager.NameNotFoundException e)
        {
            versionNumber = "X.x.x.x";
        }
        HolderUtils.configure(viewById(R.id.settings_version), getString(R.string.settings_about_version),
                versionNumber, -1);

        // Terms and conditions
        HolderUtils.configure(viewById(R.id.settings_about_clu), getString(R.string.settings_about_clu), null, -1);
        viewById(R.id.settings_about_clu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                IntentUtils.startWebBrowser(getActivity(), "http://www.alfresco.com/legal/agreements/cloud");
            }
        });

        // Terms and conditions
        HolderUtils.configure(viewById(R.id.settings_about_thirdparty), getString(R.string.settings_about_thirdparty),
                null, -1);
        viewById(R.id.settings_about_thirdparty).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new LibsBuilder()
                        .withActivityTitle(getString(R.string.settings_about_thirdparty))
                        .withLibraries("MaterialEdittext", "MaterialDialogs", "AndroidSwipeLayout", "CircleIndicator",
                                "Otto", "AppCompat Library").withLicenseDialog(true).withVersionShown(false)
                        .withAnimations(false).withLicenseShown(true).withFields(R.string.class.getFields())
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR).start(getActivity());
            }
        });

        return getRootView();
    }

    public void onCreate(Bundle savedInstanceState)
    {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // LIFECYCLE
    // ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onStart()
    {
        super.onStart();
        if (getActivity() instanceof MainActivity)
        {
            UIUtils.displayActionBarBack((MainActivity) getActivity());
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (getActivity() instanceof MainActivity)
        {
            UIUtils.setActionBarDefault((MainActivity) getActivity());
        }
    }

    // ///////////////////////////////////////////////////////////////////////////
    // INTERNAL
    // ///////////////////////////////////////////////////////////////////////////
    private void startPlayStore()
    {
        IntentUtils.startPlayStore(getActivity(), "com.activiti.android.app");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // BUILDER
    // ///////////////////////////////////////////////////////////////////////////
    public static Builder with(FragmentActivity activity)
    {
        return new Builder(activity);
    }

    public static class Builder extends LeafFragmentBuilder
    {
        // ///////////////////////////////////////////////////////////////////////////
        // CONSTRUCTORS
        // ///////////////////////////////////////////////////////////////////////////
        public Builder(FragmentActivity activity)
        {
            super(activity);
            this.extraConfiguration = new Bundle();
        }

        public Builder(FragmentActivity appActivity, Map<String, Object> configuration)
        {
            super(appActivity, configuration);
        }

        // ///////////////////////////////////////////////////////////////////////////
        // SETTERS
        // ///////////////////////////////////////////////////////////////////////////
        protected Fragment createFragment(Bundle b)
        {
            return newInstanceByTemplate(b);
        }
    }

}