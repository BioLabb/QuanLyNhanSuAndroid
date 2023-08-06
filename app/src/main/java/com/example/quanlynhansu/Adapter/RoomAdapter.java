package com.example.quanlynhansu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlynhansu.R;
import com.example.quanlynhansu.object.Room;

import java.util.List;

public class RoomAdapter extends ArrayAdapter<Room> {

    private List<Room> roomList;

    public RoomAdapter(@NonNull Context context, int room_view, List<Room> roomList) {
        super(context, 0, roomList);
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_view, parent, false);
        }

        Room room = roomList.get(position);

        TextView textViewId = convertView.findViewById(R.id.idroom);
        TextView textViewName = convertView.findViewById(R.id.nameroom);
        TextView textViewNumber = convertView.findViewById(R.id.numberroom);

        textViewId.setText(String.valueOf(room.getRoomID()));
        textViewName.setText(room.getName());
        textViewNumber.setText(String.valueOf(room.getNumber()));

        return convertView;
    }
}
