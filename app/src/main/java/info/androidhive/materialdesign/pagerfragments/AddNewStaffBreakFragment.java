package info.androidhive.materialdesign.pagerfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.adapter.add_staff_Service_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentService_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;


public class AddNewStaffBreakFragment extends Fragment {

    ToggleButton toggle_saturday, toggle_sunday, toggle_monday, toggle_tuesday,
            toggle_wednesday, toggle_thursday, toggle_friday;

    Spinner spinner_saturday_from, spinner_sunday_from, spinner_monday_from,
            spinner_tuesday_from, spinner_wednesday_from, spinner_thursday_from,
            spinner_friday_from;

    Spinner spinner_saturday_to, spinner_sunday_to, spinner_monday_to, spinner_tuesday_to,
            spinner_wednesday_to, spinner_thursday_to, spinner_friday_to;

    Button btn_submit;

    SharedPreferences sharedPreferences;

    String[] from_value, to_value;
    String[] from_value_int, to_value_int;
    int api_call_check = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_new_staff_break, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Toolbar mToolbar;
        ImageView img_back, img_profile_tool_bar, img_notifications, img_filter;
        TextView tv_app_name;

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setVisibility(View.GONE);

        img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
        img_profile_tool_bar.setVisibility(View.GONE);

        img_notifications = mToolbar.findViewById(R.id.img_notifications);
        img_notifications.setVisibility(View.VISIBLE);

        img_filter = mToolbar.findViewById(R.id.img_filter);
        img_filter.setVisibility(View.GONE);

        img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().popBackStack();
                //Toast.makeText(getActivity(),"Add staff break fragment",Toast.LENGTH_SHORT).show();
            }
        });

        toggle_saturday = rootView.findViewById(R.id.toggle_saturday);
        toggle_sunday = rootView.findViewById(R.id.toggle_sunday);
        toggle_monday = rootView.findViewById(R.id.toggle_monday);
        toggle_tuesday = rootView.findViewById(R.id.toggle_tuesday);
        toggle_wednesday = rootView.findViewById(R.id.toggle_wednesday);
        toggle_thursday = rootView.findViewById(R.id.toggle_thursday);
        toggle_friday = rootView.findViewById(R.id.toggle_friday);

        spinner_saturday_from = (Spinner) rootView.findViewById(R.id.spinner_saturday_from);
        spinner_saturday_from.setEnabled(false);
        spinner_saturday_from.setSelection(0);

        spinner_sunday_from = (Spinner) rootView.findViewById(R.id.spinner_sunday_from);
        spinner_sunday_from.setEnabled(false);
        spinner_sunday_from.setSelection(0);

        spinner_monday_from = (Spinner) rootView.findViewById(R.id.spinner_monday_from);
        spinner_monday_from.setEnabled(false);
        spinner_monday_from.setSelection(0);

        spinner_tuesday_from = (Spinner) rootView.findViewById(R.id.spinner_tuesday_from);
        spinner_tuesday_from.setEnabled(false);
        spinner_tuesday_from.setSelection(0);

        spinner_wednesday_from = (Spinner) rootView.findViewById(R.id.spinner_wednesday_from);
        spinner_wednesday_from.setEnabled(false);
        spinner_wednesday_from.setSelection(0);

        spinner_thursday_from = (Spinner) rootView.findViewById(R.id.spinner_thursday_from);
        spinner_thursday_from.setEnabled(false);
        spinner_thursday_from.setSelection(0);

        spinner_friday_from = (Spinner) rootView.findViewById(R.id.spinner_friday_from);
        spinner_friday_from.setEnabled(false);
        spinner_friday_from.setSelection(0);

        spinner_saturday_to = (Spinner) rootView.findViewById(R.id.spinner_saturday_to);
        spinner_saturday_to.setEnabled(false);
        spinner_saturday_to.setSelection(12);

        spinner_sunday_to = (Spinner) rootView.findViewById(R.id.spinner_sunday_to);
        spinner_sunday_to.setEnabled(false);
        spinner_sunday_to.setSelection(12);

        spinner_monday_to = (Spinner) rootView.findViewById(R.id.spinner_monday_to);
        spinner_monday_to.setEnabled(false);
        spinner_monday_to.setSelection(12);

        spinner_tuesday_to = (Spinner) rootView.findViewById(R.id.spinner_tuesday_to);
        spinner_tuesday_to.setEnabled(false);
        spinner_tuesday_to.setSelection(12);

        spinner_wednesday_to = (Spinner) rootView.findViewById(R.id.spinner_wednesday_to);
        spinner_wednesday_to.setEnabled(false);
        spinner_wednesday_to.setSelection(12);

        spinner_thursday_to = (Spinner) rootView.findViewById(R.id.spinner_thursday_to);
        spinner_thursday_to.setEnabled(false);
        spinner_thursday_to.setSelection(12);

        spinner_friday_to = (Spinner) rootView.findViewById(R.id.spinner_friday_to);
        spinner_friday_to.setEnabled(false);
        spinner_friday_to.setSelection(12);


        if (!sharedPreferences.getString("staff_id", "").equals("")) {
            //.makeText(getActivity(),"show_staff_detail = ",Toast.LENGTH_SHORT).show();

            //http://192.168.100.14/monshiapp/app/staff_break.php?staff_id=101&business_id=5
            String url = getResources().getString(R.string.url) + "staff_break.php";

            String params = "staff_id=" + sharedPreferences.getString("staff_id", "")+
                            "&business_id=" + sharedPreferences.getString("selected_business", "");

            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                @Override
                public void TaskCompletionResult(String result) {

                    try {

                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getString("status").equals("200")) {




                            JSONArray staff_break_list = jsonObject.getJSONArray("staff_break_list");
                            for (int i = 0; i < staff_break_list.length(); i++) {

                                JSONObject c = staff_break_list.getJSONObject(i);

                                String day_per = c.getString("day_per");
                                String day = c.getString("day");
                                String business_work = c.getString("business_work");
                                String staff_work = c.getString("staff_work");


                                if (i == 0) {
                                    toggle_saturday.setTag(business_work);
                                }
                                if (i == 1) {
                                    toggle_sunday.setTag(business_work);
                                }
                                if (i == 2) {
                                    toggle_monday.setTag(business_work);
                                }
                                if (i == 3) {
                                    toggle_tuesday.setTag(business_work);
                                }
                                if (i == 4) {
                                    toggle_wednesday.setTag(business_work);
                                }
                                if (i == 5) {
                                    toggle_thursday.setTag(business_work);
                                }
                                if (i == 6) {
                                    toggle_friday.setTag(business_work);
                                }

                                toggle_saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_saturday.getTag().equals("yes")) {
                                                spinner_saturday_from.setEnabled(true);
                                                spinner_saturday_to.setEnabled(true);
                                            } else {
                                                Toast.makeText(getActivity(), getString(R.string.str_business_is_off), Toast.LENGTH_SHORT).show();
                                                toggle_saturday.setChecked(false);
                                            }

                                        } else {
                                            spinner_saturday_from.setEnabled(false);
                                            spinner_saturday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_sunday.getTag().equals("yes")) {
                                                spinner_sunday_from.setEnabled(true);
                                                spinner_sunday_to.setEnabled(true);
                                            } else {
                                                Toast.makeText(getActivity(), getString(R.string.str_business_is_off), Toast.LENGTH_SHORT).show();
                                                toggle_sunday.setChecked(false);
                                            }

                                        } else {
                                            spinner_sunday_from.setEnabled(false);
                                            spinner_sunday_to.setEnabled(false);
                                        }
                                    }
                                });

                                toggle_monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_monday.getTag().equals("yes")) {
                                                spinner_monday_from.setEnabled(true);
                                                spinner_monday_to.setEnabled(true);
                                            } else {
                                                Toast.makeText(getActivity(), getString(R.string.str_business_is_off), Toast.LENGTH_SHORT).show();
                                                toggle_monday.setChecked(false);
                                            }

                                        } else {
                                            spinner_monday_from.setEnabled(false);
                                            spinner_monday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_tuesday.getTag().equals("yes")) {
                                                spinner_tuesday_from.setEnabled(true);
                                                spinner_tuesday_to.setEnabled(true);
                                            } else {
                                                Toast.makeText(getActivity(), getString(R.string.str_business_is_off), Toast.LENGTH_SHORT).show();
                                                toggle_tuesday.setChecked(false);
                                            }

                                        } else {
                                            spinner_tuesday_from.setEnabled(false);
                                            spinner_tuesday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_wednesday.getTag().equals("yes")) {
                                                spinner_wednesday_from.setEnabled(true);
                                                spinner_wednesday_to.setEnabled(true);
                                            } else {
                                                Toast.makeText(getActivity(), getString(R.string.str_business_is_off), Toast.LENGTH_SHORT).show();
                                                toggle_wednesday.setChecked(false);
                                            }

                                        } else {
                                            spinner_wednesday_from.setEnabled(false);
                                            spinner_wednesday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_thursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_thursday.getTag().equals("yes")) {
                                                spinner_thursday_from.setEnabled(true);
                                                spinner_thursday_to.setEnabled(true);
                                            } else {
                                                Toast.makeText(getActivity(), getString(R.string.str_business_is_off), Toast.LENGTH_SHORT).show();
                                                toggle_thursday.setChecked(false);
                                            }

                                        } else {
                                            spinner_thursday_from.setEnabled(false);
                                            spinner_thursday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_friday.getTag().equals("yes")) {
                                                spinner_friday_from.setEnabled(true);
                                                spinner_friday_to.setEnabled(true);
                                            } else {
                                                Toast.makeText(getActivity(), getString(R.string.str_business_is_off), Toast.LENGTH_SHORT).show();
                                                toggle_friday.setChecked(false);
                                            }

                                        } else {
                                            spinner_friday_from.setEnabled(false);
                                            spinner_friday_to.setEnabled(false);
                                        }
                                    }
                                });


                                if (business_work.equals("yes")) {

                                    if (staff_work.equals("yes")) {
                                        if (i == 0) {
                                            toggle_saturday.setChecked(true);
                                            spinner_saturday_from.setEnabled(true);
                                            spinner_saturday_to.setEnabled(true);
                                        }
                                        if (i == 1) {
                                            toggle_sunday.setChecked(true);
                                            spinner_sunday_from.setEnabled(true);
                                            spinner_sunday_to.setEnabled(true);
                                        }
                                        if (i == 2) {
                                            toggle_monday.setChecked(true);
                                            spinner_monday_from.setEnabled(true);
                                            spinner_monday_to.setEnabled(true);
                                        }
                                        if (i == 3) {
                                            toggle_tuesday.setChecked(true);
                                            spinner_tuesday_from.setEnabled(true);
                                            spinner_tuesday_to.setEnabled(true);
                                        }
                                        if (i == 4) {
                                            toggle_wednesday.setChecked(true);
                                            spinner_wednesday_from.setEnabled(true);
                                            spinner_wednesday_to.setEnabled(true);
                                        }
                                        if (i == 5) {
                                            toggle_thursday.setChecked(true);
                                            spinner_thursday_from.setEnabled(true);
                                            spinner_thursday_to.setEnabled(true);
                                        }
                                        if (i == 6) {
                                            toggle_friday.setChecked(true);
                                            spinner_friday_from.setEnabled(true);
                                            spinner_friday_to.setEnabled(true);
                                        }
                                    }
                                }

                                //Toast.makeText(getActivity(),"day = "+day,Toast.LENGTH_SHORT).show();
                                System.out.println("day = " + day);

                                JSONArray from_time_array = c.getJSONArray("staff_start_time_arr");

                                from_value = new String[from_time_array.length()];
                                from_value_int = new String[from_time_array.length()];

                                int from_index = 0;
//                                Toast.makeText(getActivity(),"from_value = "+from_value.length,Toast.LENGTH_LONG).show();

                                for (int j = 0; j < from_time_array.length(); j++) {
                                    JSONObject d = from_time_array.getJSONObject(j);

                                    String value = d.getString("time");
                                    String value_in_per = d.getString("time_per");
                                    String status_from = d.getString("select");

                                    from_value[j] = value_in_per;
                                    from_value_int[j] = value;

                                    //Toast.makeText(getActivity(),"value from = "+value,Toast.LENGTH_SHORT).show();

                                    if (status_from.equals("yes")) {
                                        from_index = j;
                                    }

                                    System.out.println("value from = " + value);

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, from_value) {

                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getView(position, convertView, parent);

                                        return v;
                                    }

                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getDropDownView(position, convertView, parent);
                                        //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                        return v;
                                    }
                                };

                                if (i == 0) {
                                    spinner_saturday_from.setAdapter(adapter);
                                    spinner_saturday_from.setSelection(from_index);
                                }

                                if (i == 1) {
                                    spinner_sunday_from.setAdapter(adapter);
                                    spinner_sunday_from.setSelection(from_index);
                                }
                                if (i == 2) {
                                    spinner_monday_from.setAdapter(adapter);
                                    spinner_monday_from.setSelection(from_index);
                                }

                                if (i == 3) {
                                    spinner_tuesday_from.setAdapter(adapter);
                                    spinner_tuesday_from.setSelection(from_index);
                                }
                                if (i == 4) {
                                    spinner_wednesday_from.setAdapter(adapter);
                                    spinner_wednesday_from.setSelection(from_index);
                                }
                                if (i == 5) {
                                    spinner_thursday_from.setAdapter(adapter);
                                    spinner_thursday_from.setSelection(from_index);
                                }
                                if (i == 6) {
                                    spinner_friday_from.setAdapter(adapter);
                                    spinner_friday_from.setSelection(from_index);
                                }

                                JSONArray to_time_array = c.getJSONArray("staff_end_time_arr");

                                to_value = new String[to_time_array.length()];
                                to_value_int = new String[to_time_array.length()];

                                int to_index = 0;

                                for (int j = 0; j < to_time_array.length(); j++) {
                                    JSONObject d = to_time_array.getJSONObject(j);
                                    String value = d.getString("time");
                                    String value_in_per = d.getString("time_per");
                                    String status_to = d.getString("select");

                                    to_value[j] = value_in_per;
                                    to_value_int[j] = value;

                                    if (status_to.equals("yes")) {
                                        to_index = j;
                                    }

                                    // Toast.makeText(getActivity(),"value to = "+value,Toast.LENGTH_SHORT).show();
                                    System.out.println("value to = " + value);
                                }

                                ArrayAdapter<String> adapter_ = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, to_value) {

                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getView(position, convertView, parent);

                                        return v;
                                    }

                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getDropDownView(position, convertView, parent);
                                        //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                        return v;
                                    }
                                };

                                if (i == 0) {
                                    spinner_saturday_to.setAdapter(adapter);
                                    spinner_saturday_to.setSelection(to_index);
                                }

                                if (i == 1) {
                                    spinner_sunday_to.setAdapter(adapter);
                                    spinner_sunday_to.setSelection(to_index);
                                }
                                if (i == 2) {
                                    spinner_monday_to.setAdapter(adapter);
                                    spinner_monday_to.setSelection(to_index);
                                }

                                if (i == 3) {
                                    spinner_tuesday_to.setAdapter(adapter);
                                    spinner_tuesday_to.setSelection(to_index);
                                }

                                if (i == 4) {
                                    spinner_wednesday_to.setAdapter(adapter);
                                    spinner_wednesday_to.setSelection(to_index);
                                }
                                if (i == 5) {
                                    spinner_thursday_to.setAdapter(adapter);
                                    spinner_thursday_to.setSelection(to_index);
                                }
                                if (i == 6) {
                                    spinner_friday_to.setAdapter(adapter);
                                    spinner_friday_to.setSelection(to_index);
                                }
                            }
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            webRequestCall.execute(url, "POST", params);
        }


        btn_submit = rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String check_operating_day = "";
                String from = "", to = "";
                String day_on_off = "";


                if (toggle_saturday.isChecked()) {
                    check_operating_day += "Saturday,";

                    from += from_value_int[spinner_saturday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_saturday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "on,";
                } else {
                    from += from_value_int[spinner_saturday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_saturday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "off,";
                }
                if (toggle_sunday.isChecked()) {
                    check_operating_day += "Sunday,";
                    from += from_value_int[spinner_sunday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_sunday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "on,";
                } else {
                    from += from_value_int[spinner_sunday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_sunday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "off,";
                }
                if (toggle_monday.isChecked()) {
                    check_operating_day += "Monday,";
                    from += from_value_int[spinner_monday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_monday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "on,";
                } else {
                    from += from_value_int[spinner_monday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_monday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "off,";
                }
                if (toggle_tuesday.isChecked()) {
                    check_operating_day += "Tuesday,";
                    from += from_value_int[spinner_tuesday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_tuesday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "on,";
                } else {
                    from += from_value_int[spinner_tuesday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_tuesday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "off,";
                }
                if (toggle_wednesday.isChecked()) {
                    check_operating_day += "Wednesday,";
                    from += from_value_int[spinner_wednesday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_wednesday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "on,";
                } else {
                    from += from_value_int[spinner_wednesday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_wednesday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "off,";
                }
                if (toggle_thursday.isChecked()) {
                    check_operating_day += "Thursday,";
                    from += from_value_int[spinner_thursday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_thursday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "on,";
                } else {
                    from += from_value_int[spinner_thursday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_thursday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "off,";
                }
                if (toggle_friday.isChecked()) {
                    check_operating_day += "Friday,";
                    from += from_value_int[spinner_friday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_friday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "on,";
                } else {
                    from += from_value_int[spinner_friday_from.getSelectedItemPosition()] + ",";
                    to += to_value_int[spinner_friday_to.getSelectedItemPosition()] + ",";
                    day_on_off += "off,";
                }


                //staff_break_update.php?business_id=&staff_id=&all_operating_day=&check_operating_day=&from_hour=&to_hour=
                String url    =  getResources().getString(R.string.url)+"staff_break_update.php";


                String params = "staff_id=" + sharedPreferences.getString("staff_id", "")+
                                "&business_id=" + sharedPreferences.getString("selected_business", "")+
                                "&all_operating_day=Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday"+
                                "&check_operating_day="+removeLastChar(check_operating_day)+
                                "&day_on_off=" + removeLastChar(day_on_off)+
                                "&from_hour=" + removeLastChar(from)+
                                "&to_hour=" + removeLastChar(to);

                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                    @Override
                    public void TaskCompletionResult(String result) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getString("status").equals("200")) {
                                Toast.makeText(getActivity(), jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();
                            }
                            else{
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                webRequestCall.execute(url, "POST", params);
            }
        });

            return rootView;
        }

    private static String removeLastChar(String str) {

        if(str.equals("")){
            return "";
        }else
            return str.substring(0, str.length() - 1);
    }


}
