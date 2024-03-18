package com.example.lab4memopaddcp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DefaultModel extends AbstractModel{

DatabaseHandler db;

List<Memo> oldOutput, newOutput = new List<Memo>() {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return false;
    }

    @NonNull
    @Override
    public Iterator<Memo> iterator() {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return null;
    }

    @Override
    public boolean add(Memo memo) {
        return false;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends Memo> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends Memo> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Memo get(int index) {
        return null;
    }

    @Override
    public Memo set(int index, Memo element) {
        return null;
    }

    @Override
    public void add(int index, Memo element) {

    }

    @Override
    public Memo remove(int index) {
        return null;
    }

    @Override
    public int indexOf(@Nullable Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<Memo> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<Memo> listIterator(int index) {
        return null;
    }

    @NonNull
    @Override
    public List<Memo> subList(int fromIndex, int toIndex) {
        return null;
    }
};

public DefaultModel(DatabaseHandler db){
    this.db = db;
}
    public void initDefault(){

    }

    public void setAddMemo(String memo){
        db.addMemo(new Memo(memo));
        setGetAllMemos();
    }

    public void setDeleteMemo(int position){
        db.deleteMemo(position);
        setGetAllMemos();
    }

    public void setGetAllMemos(){
        oldOutput = newOutput;
        newOutput = db.getAllMemosAsList();


        firePropertyChange(DefaultController.ELEMENT_GETALLMEMOS_PROPERTY,oldOutput,newOutput);
    }
}
