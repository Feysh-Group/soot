package soot.util;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2002 Ondrej Lhotak
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**
 * A class that numbers objects, so they can be placed in bitsets.
 *
 * @author Notify
 */
public class PagNodeArrayNumberer<E extends Numberable> extends ArrayNumberer<E> {

  class InnerArrayNumberer<T extends E> implements IterableNumberer<T> {

    final Set<T> objContainer = new HashSet<>(1024);

    @Override
    public Iterator<T> iterator() {
      return objContainer.iterator();
    }

    @Override
    public void add(T o) {
      PagNodeArrayNumberer.this.add(o);
      objContainer.add(o);
    }

    @Override
    public boolean remove(T o) {
      return objContainer.remove(o);
    }

    @Override
    public long get(T o) {
      if (o == null) {
        return 0;
      }
      if (objContainer.contains(o)) {
        return PagNodeArrayNumberer.this.get(o);
      }
      return 0;
    }

    @Override
    public T get(long number) {
      E o = PagNodeArrayNumberer.this.get(number);
      if (o != null && objContainer.contains(o)) {
        //noinspection unchecked
        return (T) o;
      }
      return null;
    }

    @Override
    public int size() {
      return objContainer.size();
    }
  }

  public <T extends E> IterableNumberer<T> getInnerNumberer() {
    return new InnerArrayNumberer<>();
  }

}
