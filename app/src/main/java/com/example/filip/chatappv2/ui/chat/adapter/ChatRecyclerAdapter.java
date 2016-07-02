package com.example.filip.chatappv2.ui.chat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.filip.chatappv2.R;
import com.example.filip.chatappv2.pojo.ChatMessage;
import com.example.filip.chatappv2.presentation.ViewHolderPresenter;
import com.example.filip.chatappv2.presentation.ViewHolderPresenterImpl;
import com.example.filip.chatappv2.view.ViewHolderView;

import java.util.ArrayList;

/**
 * Created by flip6 on 10.6.2016..
 */
public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder> {
    private final ArrayList<ChatMessage> mItemList = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewToInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_layout, parent, false);
        return new ViewHolder(viewToInflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage current = mItemList.get(position);
        holder.presenter.handleOnReceivedChatMessage(current);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void clearChatMessages() {
        mItemList.clear();
        notifyDataSetChanged();
    }

    public void addNewMessage(ChatMessage message) {
        mItemList.add(message);
        notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements ViewHolderView {
        final ImageView mUserImageView;
        final TextView mUserNameTextView;
        final TextView mMessageTextView;

        protected final ViewHolderPresenter presenter;

        public ViewHolder(View itemView) {
            super(itemView);
            mUserImageView = (ImageView) itemView.findViewById(R.id.chat_message_image_view);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.chat_message_author_text_view);
            mMessageTextView = (TextView) itemView.findViewById(R.id.chat_message_message_text_view);
            presenter = new ViewHolderPresenterImpl();
            presenter.setView(this);
        }


        @Override
        public void loadUserImage(String imageURL) {
            if (imageURL != null) {
                Glide.with(itemView.getContext()).load(imageURL).into(mUserImageView);
            }
        }

        @Override
        public void setUserDisplayName(String userDisplayName) {
            mUserNameTextView.setText(userDisplayName);
        }

        @Override
        public void setMessageText(String messageText) {
            mMessageTextView.setText(messageText);
        }

        @Override
        public void receiveChatMessage(ChatMessage message) {
            presenter.handleOnReceivedChatMessage(message);
        }

        @Override
        public void hideUserDisplayName() {
            mUserImageView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void hideUserImage() {
            mUserNameTextView.setVisibility(View.INVISIBLE);
        }
    }
}